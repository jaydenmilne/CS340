import { TestBed } from '@angular/core/testing';

import { PollerService } from './poller.service';

describe('PollerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PollerService = TestBed.get(PollerService);
    expect(service).toBeTruthy();
  });
});
