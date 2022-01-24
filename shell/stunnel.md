출처 : [https://en.wikipedia.org/wiki/Stunnel](https://en.wikipedia.org/wiki/Stunnel)

# Stunnel

**Stunnel**은 보편적인 TLS/SSL [터널링](https://en.wikipedia.org/wiki/Tunneling_protocol) 서비스에 사용되는 [오픈 소스](https://en.wikipedia.org/wiki/Open-source_software) 멀티 플랫폼 [응용프로그램](https://en.wikipedia.org/wiki/Application_software)입니다.

Stunnel은 TLS 또는 SSL을 사용하지 않는 클라이언트 또는 서버에 암호화된 보안 연결을 제공하는 데 사용할 수 있습니다. 이 프로그램은 [유닉스와 비슷한](https://en.wikipedia.org/wiki/Unix-like) 운영체제 대부분과 [윈도우즈](https://en.wikipedia.org/wiki/Microsoft_Windows)를 포함하여 다양한 운영체제에서 실행됩니다. Stunnel은 [OpenSSL](https://en.wikipedia.org/wiki/OpenSSL) [라이브러리](https://en.wikipedia.org/wiki/Library_(computing))를 사용하여 TLS 또는 SSL 프로토콜을 구현합니다.

Stunnel은 SSL 접속을 보안 연결하기 위해 [X.509](https://en.wikipedia.org/wiki/X.509) [디지털 인증서](https://en.wikipedia.org/wiki/Public_key_certificate)로 공개키 암호를 사용합니다. 클라이언트는 선택적으로 인증서를 통해 인증 받을 수 있습니다.

[libwrap](https://en.wikipedia.org/wiki/TCP_Wrappers)에 대해 링크된 경우, [프록시](https://en.wikipedia.org/wiki/Proxy_server)-[방화벽](https://en.wikipedia.org/wiki/Firewall_(computing)) 서비스로도 작동하도록 구성할 수 있습니다.

Stunnel은 Michal Trojnara에 의해 관리되고 있고 OpenSSL을 제외하고 [GNU GPL (General Public License)](https://en.wikipedia.org/wiki/GNU_General_Public_License) 조건에 따라 릴리스됩니다.

## 시나리오 예시

예를 들어, SSL이 아닌 [SMTP](https://en.wikipedia.org/wiki/Simple_Mail_Transfer_Protocol) 메일 서버로 [SSL](https://en.wikipedia.org/wiki/Transport_Layer_Security) 보안 연결을 제공하기 위해 stunnel을 사용할 수 있습니다. SMTP 서버는 25번 [포트](https://en.wikipedia.org/wiki/Port_(computer_networking))로 TCP 접속을 한다 가정하면, stunnel에 SSL 포트로 465로 설정하고 SSL이 아닌 포트를 25로 설정합니다. 메일 클라이언트는 SSL을 통해 포트 465로 접속합니다. 클라이언트의 네트워크 트래픽은 stunnel 응용프로그램으로 SSL을 통해 전달되어 트래픽을 투명하게 암호화 / 복호화하고 보안되지 않은 트래픽을 포트 25로 로컬로 전달합니다. 메일 서버는 비 SSL 메일 클라이언트로 봅니다.

stunnel 프로세스는 보안이 적용되지 않은 메일 애플리케이션과 동일하거나 다른 서버에서 실행될 수 있습니다. 그러나 두 시스템은 일반적으로 보안 내부 네트워크의 방화벽 뒤에 있습니다 (침입자가 포트 25에 직접 보안되지 않은 연결을 만들 수 없음).
