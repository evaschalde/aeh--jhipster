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

import { IResponsable, defaultValue } from 'app/shared/model/responsable.model';

export const ACTION_TYPES = {
  FETCH_RESPONSABLE_LIST: 'responsable/FETCH_RESPONSABLE_LIST',
  FETCH_RESPONSABLE: 'responsable/FETCH_RESPONSABLE',
  CREATE_RESPONSABLE: 'responsable/CREATE_RESPONSABLE',
  UPDATE_RESPONSABLE: 'responsable/UPDATE_RESPONSABLE',
  DELETE_RESPONSABLE: 'responsable/DELETE_RESPONSABLE',
  RESET: 'responsable/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IResponsable>,
  entity: defaultValue,
  links: {
    last: 0
  },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ResponsableState = Readonly<typeof initialState>;

// Reducer

export default (state: ResponsableState = initialState, action): ResponsableState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RESPONSABLE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RESPONSABLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RESPONSABLE):
    case REQUEST(ACTION_TYPES.UPDATE_RESPONSABLE):
    case REQUEST(ACTION_TYPES.DELETE_RESPONSABLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RESPONSABLE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RESPONSABLE):
    case FAILURE(ACTION_TYPES.CREATE_RESPONSABLE):
    case FAILURE(ACTION_TYPES.UPDATE_RESPONSABLE):
    case FAILURE(ACTION_TYPES.DELETE_RESPONSABLE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESPONSABLE_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links: { last: links.last },
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESPONSABLE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RESPONSABLE):
    case SUCCESS(ACTION_TYPES.UPDATE_RESPONSABLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RESPONSABLE):
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

const apiUrl = SERVER_API_URL + '/api/responsables';

// Actions

export const getEntities: ICrudGetAllAction<IResponsable> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_RESPONSABLE_LIST,
    payload: axios.get<IResponsable>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IResponsable> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RESPONSABLE,
    payload: axios.get<IResponsable>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IResponsable> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RESPONSABLE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IResponsable> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RESPONSABLE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IResponsable> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RESPONSABLE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
