import {myFactory} from './myFactory';

describe('myFactory', () => {
  it('should create an instance', () => {
    expect(new myFactory()).toBeTruthy();
  });
});
