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

import { IBranchOffice, defaultValue } from 'app/shared/model/branch-office.model';

export const ACTION_TYPES = {
  FETCH_BRANCHOFFICE_LIST: 'branchOffice/FETCH_BRANCHOFFICE_LIST',
  FETCH_BRANCHOFFICE: 'branchOffice/FETCH_BRANCHOFFICE',
  CREATE_BRANCHOFFICE: 'branchOffice/CREATE_BRANCHOFFICE',
  UPDATE_BRANCHOFFICE: 'branchOffice/UPDATE_BRANCHOFFICE',
  DELETE_BRANCHOFFICE: 'branchOffice/DELETE_BRANCHOFFICE',
  RESET: 'branchOffice/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBranchOffice>,
  entity: defaultValue,
  links: {
    last: 0
  },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type BranchOfficeState = Readonly<typeof initialState>;

// Reducer

export default (state: BranchOfficeState = initialState, action): BranchOfficeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BRANCHOFFICE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BRANCHOFFICE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BRANCHOFFICE):
    case REQUEST(ACTION_TYPES.UPDATE_BRANCHOFFICE):
    case REQUEST(ACTION_TYPES.DELETE_BRANCHOFFICE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BRANCHOFFICE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BRANCHOFFICE):
    case FAILURE(ACTION_TYPES.CREATE_BRANCHOFFICE):
    case FAILURE(ACTION_TYPES.UPDATE_BRANCHOFFICE):
    case FAILURE(ACTION_TYPES.DELETE_BRANCHOFFICE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BRANCHOFFICE_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links: { last: links.last },
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_BRANCHOFFICE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BRANCHOFFICE):
    case SUCCESS(ACTION_TYPES.UPDATE_BRANCHOFFICE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BRANCHOFFICE):
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

const apiUrl = SERVER_API_URL + '/api/branch-offices';

// Actions

export const getEntities: ICrudGetAllAction<IBranchOffice> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_BRANCHOFFICE_LIST,
    payload: axios.get<IBranchOffice>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IBranchOffice> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BRANCHOFFICE,
    payload: axios.get<IBranchOffice>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBranchOffice> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BRANCHOFFICE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBranchOffice> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BRANCHOFFICE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBranchOffice> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BRANCHOFFICE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
