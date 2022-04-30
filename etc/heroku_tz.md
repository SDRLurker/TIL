참고주소 : [https://dev.to/paulasantamaria/change-the-timezone-on-a-heroku-app-2b4](https://dev.to/paulasantamaria/change-the-timezone-on-a-heroku-app-2b4)

# Heroku 앱에서 타임 존(time zone) 변경하기

저는 몇 주 전에 [Heroku](https://www.heroku.com/)를 발경하였습니다. 특히 제 팀이 원격으로 개발 환경을 사용하는 무료 플랜이 있기 때문에 당신의 앱을 배포하는 멋진 플랫폼입니다.

최근 저는 새로운 프로젝트 작업을 시작했습니다. 이 특별한 프로젝트에서 주요 연산의 대부분은 **날짜**와 관련되어 있습니다. 앱은 프론트 엔드를 Angular 5로 만들었고 백 엔드는 Node.js로 만들었습니다.

모든 것은 제 로컬 환경에서 완벽히 작동하였지만 제가 Heroku로 배포하였을 때 모든 날짜 연산은 임의로 작동하기 시작하였습니다.(스포일러: 랜덤은 아닙니다)

여기에 방법이 있습니다. 저는 아르헨티나 출신이고, 로컬 서버에서 배포된 프론트 엔드가 있지만 백엔드는 Heroku에 있고 새로운 Heroku 앱의 기본 지역은 "미국(us)" 이었습니다. 기본적으로 제 프론트 엔드와 백 엔드는 **다른 타임 존** 이었습니다.

이런 종류의 날짜 문제를 다루는 것은 이번이 처음이 아닙니다(내 지역에는 로컬 클라우드 서비스가 많지 않음). 그래서 나는 거의 즉시 문제의 원인을 찾았고, 혹시라도 비슷한 문제가 발생할 경우를 대비하여 공유하고 싶었습니다.

운이 좋게도 Heroku는 우리에게 새로운 설정 변수를 추가함으로서 우리의 앱의 타임 존을 변경할 수 있도록 합니다.

## Heroku CLI에서

1.  명령어 shell을 엽니다.
2.  Heroku로 로그인 합니다.
3.  당신의 앱 디렉터리를 찻습니다.
4.  다음처럼 TZ를 설정합니다.

```
heroku config:add TZ="America/Argentina/Buenos_Aires"
```

## Heroku dashboard(web)에서

1.  브라우저에서 Heroku dashboard로 로그인 합니다.
2.  앱을 찻습니다.
3.  Settings 탭을 클릭합니다.
4.  "Reveal Config Vars" 버튼을 누릅니다.
5.  "TZ"를 키로 설정하고 타입 존의 값(예:America/Argentina/Buenos\_Aires)을 설정합니다.
6.  Add(추가) 버튼을 누릅니다.

## 올바른 TZ값인지 어떻게 알 수 있나요

다음 [링크](https://en.wikipedia.org/wiki/List_of_tz_database_time_zones)에서 타임 존의 전체 목록을 확인할 수 있습니다.

## 새로운 TZ 값 확인하기

Heroku CLI에서 새로운 설정 변수의 값을 다음 명령어로 확인할 수 있습니다.

```
heroku config:get TZ
```

## Heroku에 대한 더 많은 정보

[Heroku가 무엇입니까?](https://www.heroku.com/what)

## Heroku 설정 변수에 대한 더 많은 정보

[설정 변수](https://devcenter.heroku.com/articles/config-vars)
