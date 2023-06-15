import { Size } from 'app/entities/enumerations/size.model';

import { IProduct, NewProduct } from './product.model';

export const sampleWithRequiredData: IProduct = {
  id: 77672,
  name: 'Customer-focused',
  price: 96307,
  productSize: Size['XL'],
};

export const sampleWithPartialData: IProduct = {
  id: 7747,
  name: 'Programmable Networked deliver',
  price: 39641,
  productSize: Size['XXL'],
  image: 'driver synthesizing',
};

export const sampleWithFullData: IProduct = {
  id: 85387,
  name: 'bluetooth District driver',
  description: 'Customer-focused Island',
  price: 13074,
  productSize: Size['M'],
  image: 'Incredible',
};

export const sampleWithNewData: NewProduct = {
  name: 'Dynamic encompassing vertical',
  price: 30768,
  productSize: Size['XL'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
