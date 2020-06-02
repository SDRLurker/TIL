#!/usr/bin/python3

import asyncio
import websockets
import socket
import json

async def subscribe_without_login(url, channels):
    REPLY_TIMEOUT = 10
    PING_TIMEOUT = 1
    SLEEP_TIME = 1
    sub_param = {"type":"transaction", "symbols":channels}
    sub_str = json.dumps(sub_param)

    while True:
        try:
            # https://stackoverflow.com/questions/54101923/1006-connection-closed-abnormally-error-with-python-3-7-websockets
            async with websockets.connect(url, ping_interval=None) as ws:
                await ws.send(sub_str)
                #print(f"send: {sub_str}")
                while True:
                    try:
                        res = await asyncio.wait_for(ws.recv(),
                            timeout=REPLY_TIMEOUT)
                    except (asyncio.TimeoutError, websockets.exceptions.ConnectionClosed) as e:
                        try:
                            pong = await ws.ping()
                            await asyncio.wait_for(pong, timeout=PING_TIMEOUT)
                            print('Ping OK, keeping connection alive...')
                            continue
                        except:
                            print('Ping error - retrying connection in %d sec' % SLEEP_TIME, e)
                            await asyncio.sleep(SLEEP_TIME)
                            break

                    #res = inflate(res)
                    print("%s" % res)
        except socket.gaierror:
            print('Socket error - retrying connection in %d sec' % SLEEP_TIME)
            await asyncio.sleep(SLEEP_TIME)
            continue
        except ConnectionRefusedError:
            print('Nobody seems to listen to this endpoint. Please check the URL.')
            print('Retrying connection in %d sec.' % SLEEP_TIME)
            await asyncio.sleep(SLEEP_TIME)
            continue

url = 'wss://pubwss.bithumb.com/pub/ws'
channels = ["BTC_KRW" , "ETH_KRW", "BCH_KRW", "XRP_KRW", "EOS_KRW"]
#channels = ["ALL_KRW"]

if __name__ == "__main__":
    asyncio.get_event_loop().run_until_complete(subscribe_without_login(url, channels))
