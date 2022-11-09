출처 : [https://note.nkmk.me/en/python-for-enumerate-zip/](https://tecsun.medium.com/how-to-reset-anydesk-id-1a6b517460b9)

# AnyDesk ID 리셋하는 방법?

원격 IT 지원 담당자가 사용자가 없을 때 PC에 자동으로 액세스하는 것을 원하지 않으면 어떻게 해야 합니까?

리셋을 강제하는 간단한 방법이 있습니다. 실행중인 AnyDesk 프로세스(인스턴스)를 종료하고, C:\\ProgramData\\Anydesk로 갑니다. 당신의 ID와 Alias는 service.conf에 저장되어 있습니다.

저는 어떤 설정 파일이라도 직접 삭제하는 것을 충고하고 싶지 않습니다. 이는 몇년동안 서버를 다루면서 교훈을 얻기 어렵기 때문입니다.

복사본을 백업하는 것은 항상 좋습니다. 그 파일을 service\_backup.conf로 변경합니다.

이제 AnyDesk를 실행하면, 새로운 service.conf를 자동으로 생성할 것입니다. 짜잔, 당신의 AnyDesk ID를 보십시오! 당신은 새로운 ID를 얻었습니다!
