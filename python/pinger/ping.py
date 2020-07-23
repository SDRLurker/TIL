from ping3 import ping, verbose_ping

import sys
import platform
import base64
import ctypes
import threading

from cefpython3 import cefpython as cef
import asyncio
import functools


def main():
    sys.excepthook = cef.ExceptHook
    app_settings = {
    }
    cef.Initialize(settings=app_settings)

    browser_settings = {
    }
    browser = cef.CreateBrowserSync(url='file:///web/index.html',
                                    settings=browser_settings,
                                    window_title='Pinger V0.1')
    if platform.system() == "Windows":
        window_handle = browser.GetOuterWindowHandle()
        insert_after_handle = 0
        # X and Y parameters are ignored by setting the SWP_NOMOVE flag
        SWP_NOMOVE = 0x0002
        # noinspection PyUnresolvedReferences
        ctypes.windll.user32.SetWindowPos(window_handle, insert_after_handle,
                                          0, 0, 700, 500, SWP_NOMOVE)
    set_javascript_bindings(browser)
    cef.MessageLoop()
    cef.Shutdown()


class External():
    target_dir_path = ''
    def __init__(self, browser):
        self.browser = browser
        self.loop = asyncio.get_event_loop()
        
    def __del__(self):
        self.loop.close()

    async def pingpong(self, i, url):
        r = await self.loop.run_in_executor(
            None, functools.partial(ping, url, timeout=1))
        if r is None:
            r = 0
        print(url, i, r)
        await self.loop.run_in_executor(
            None, self.browser.ExecuteFunction, 'updateRes', i, url, r)

    async def main(self, arr):
        futures = [asyncio.ensure_future(self.pingpong(i, url)) for i, url in enumerate(arr)]
        result = await asyncio.gather(*futures)

    def ask_ping(self, arr):
        self.loop.run_until_complete(self.main(arr))


def set_javascript_bindings(browser):
    external = External(browser)
    bindings = cef.JavascriptBindings(
        bindToFrames=False, bindToPopups=False)
    bindings.SetObject('external', external)
    browser.SetJavascriptBindings(bindings)


if __name__ == "__main__":
    main()
