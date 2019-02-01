import { TestBed } from '@angular/core/testing';

import { ServerProxyService } from './server-proxy.service';

describe('ServerProxyService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ServerProxyService = TestBed.get(ServerProxyService);
    expect(service).toBeTruthy();
  });
});
