**출처**

[https://serverfault.com/questions/394815/how-to-update-curl-ca-bundle-on-redhat](https://serverfault.com/questions/394815/how-to-update-curl-ca-bundle-on-redhat)

## Redhat에서 cURL CA 번들 업데이트하는 방법?

저는 cURL이 기한이 지난 버전으로 CA 번들에 관한 문제가 있습니다.

```
curl: (60) SSL certificate problem, verify that the CA cert is OK. Details:
error:14090086:SSL routines:SSL3_GET_SERVER_CERTIFICATE:certificate verify failed
More details here: http://curl.haxx.se/docs/sslcerts.html
```

제가 필요한 것이나 어떻게 해야하는지 이해하지 못했기 때문에 문서를 읽는 것은 도움이 되지 않았습니다. 저는 CA 번들을 업데이트할 필요가 있고 Redhat에서 실행할 것입니다. 업데이트 Redhat에서 CA 번들을 업데이트 하려면 필요한 게 무엇이 있을까요?

---

### 7개의 답변 중 2개의 답변

**RHEL 6 이상에서,** 당신은 아래 답변에 lzap님이 설명한대로 **update-ca-trust**를 사용해야 합니다.

Fedora, CentOS, Redhat 이전버전 :

Curl은 /etc/pki/tls/certs/ca-bundle.crt에 저장된 시스템 기본 CA 번들을 사용합니다. 이것을 변경하기 전에 만약 필요하다면 시스템 기본값으로 복구할 수 있도록 이 파일의 복사본을 만드세요. 당신은 파일에 새로운 CA 인증서를 추가하거나 전체 번들을 대체할 수 있습니다.

인증서를 얻는 곳에 대해 궁금하십니까? 저는 curl.se/ca를 추천합니다. 다음 한 줄이면 됩니다.

```
curl https://curl.se/ca/cacert.pem -o /etc/pki/tls/certs/ca-bundle.crt
```

Fedora Core 2 위치는 `/usr/share/ssl/certs/ca-bundle.crt` 입니다.

---

### lzap 답변

RHEL 6+ 시스템에서 할 수 있는 추천하는 방법은 기본으로 설치된 **update-ca-trust** 툴을 사용하는 것입니다.

```
# cat /etc/pki/ca-trust/source/README 
This directory /etc/pki/ca-trust/source/ contains CA certificates and 
trust settings in the PEM file format. The trust settings found here will be
interpreted with a high priority - higher than the ones found in 
/usr/share/pki/ca-trust-source/.

=============================================================================
QUICK HELP: To add a certificate in the simple PEM or DER file formats to the
            list of CAs trusted on the system:

            Copy it to the
                    /etc/pki/ca-trust/source/anchors/
            subdirectory, and run the
                    update-ca-trust
            command.

            If your certificate is in the extended BEGIN TRUSTED file format,
            then place it into the main source/ directory instead.
=============================================================================

Please refer to the update-ca-trust(8) manual page for additional information
```

따라서 crt 파일을 /etc/pki/ca-trust/source/anchors/에 드롭하고 도구를 실행하기만 하면 됩니다. 이 작업은 안전하며 백업을 수행할 필요가 없습니다. 전체 매뉴얼 페이지는 [https://www.mankier.com/8/update-ca-trust](https://www.mankier.com/8/update-ca-trust)에서 찾을 수 있습니다.
