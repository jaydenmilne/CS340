import { InjectionToken, FactoryProvider } from '@angular/core';

export const WINDOW = new InjectionToken<Window>('window');

/**
 * Angular boilerplate to be able to get a reference to the window so we
 * can get the current URL.
 */
const windowProvider: FactoryProvider = {
  provide: WINDOW,
  useFactory: () => window
};

export const WINDOW_PROVIDERS = [
    windowProvider
]