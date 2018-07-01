import { isEqual } from 'lodash';
import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { SERVER_API_URL } from 'app/config/constants';

import { IExternalResponsable, defaultValue } from 'app/shared/model/external-responsable.model';

export const ACTION_TYPES = {
  FETCH_EXTERNALRESPONSABLE_LIST: 'externalResponsable/FETCH_EXTERNALRESPONSABLE_LIST',
  FETCH_EXTERNALRESPONSABLE: 'externalResponsable/FETCH_EXTERNALRESPONSABLE',
  CREATE_EXTERNALRESPONSABLE: 'externalResponsable/CREATE_EXTERNALRESPONSABLE',
  UPDATE_EXTERNALRESPONSABLE: 'externalResponsable/UPDATE_EXTERNALRESPONSABLE',
  DELETE_EXTERNALRESPONSABLE: 'externalResponsable/DELETE_EXTERNALRESPONSABLE',
  RESET: 'externalResponsable/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IExternalResponsable>,
  entity: defaultValue,
  links: {
    last: 0
  },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ExternalResponsableState = Readonly<typeof initialState>;

// Reducer

export default (state: ExternalResponsableState = initialState, action): ExternalResponsableState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EXTERNALRESPONSABLE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EXTERNALRESPONSABLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EXTERNALRESPONSABLE):
    case REQUEST(ACTION_TYPES.UPDATE_EXTERNALRESPONSABLE):
    case REQUEST(ACTION_TYPES.DELETE_EXTERNALRESPONSABLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EXTERNALRESPONSABLE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EXTERNALRESPONSABLE):
    case FAILURE(ACTION_TYPES.CREATE_EXTERNALRESPONSABLE):
    case FAILURE(ACTION_TYPES.UPDATE_EXTERNALRESPONSABLE):
    case FAILURE(ACTION_TYPES.DELETE_EXTERNALRESPONSABLE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXTERNALRESPONSABLE_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links: { last: links.last },
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXTERNALRESPONSABLE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EXTERNALRESPONSABLE):
    case SUCCESS(ACTION_TYPES.UPDATE_EXTERNALRESPONSABLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EXTERNALRESPONSABLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = SERVER_API_URL + '/api/external-responsables';

// Actions

export const getEntities: ICrudGetAllAction<IExternalResponsable> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EXTERNALRESPONSABLE_LIST,
    payload: axios.get<IExternalResponsable>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IExternalResponsable> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EXTERNALRESPONSABLE,
    payload: axios.get<IExternalResponsable>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IExternalResponsable> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EXTERNALRESPONSABLE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IExternalResponsable> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EXTERNALRESPONSABLE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IExternalResponsable> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EXTERNALRESPONSABLE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
