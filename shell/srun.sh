#!/bin/bash
EXECUTE=`basename $0`
SERVER=$1
CMD=$2
TARGETPATH=$3
GROUP="GROUP"        # 넘길서버의 동일한 부분의 도메인이나 IP를 GROUP 자리에 입력합니다.

usage()
{

local MESSAGE=$1
echo "-----------------------------------------------------------------------"
echo "사용방법 1) $EXECUTE [실행서버] [실행cmd] [실행위치]"
echo "-----------------------------------------------------------------------"
echo "사용방법 2) $EXECUTE [실행서버] [실행cmd] "
echo "          [실행위치]를 지정하지 않으면, [실행파일]과 동일 위치에서 실행"
echo "-----------------------------------------------------------------------"
echo "실행서버) 쉼표로 구분하여 IP 마지막 자리만 입력"
echo "          $EXECUTE 1,2,3,4"
echo "          => 현재서버에서 ${GROUP}1,...,${GROUP}4로 명령을 실행합니다."
echo "-----------------------------------------------------------------------"
echo $MESSAGE
echo "-----------------------------------------------------------------------"

}

if [ -z "$SERVER" ]; then
    usage "상태) SERVER 파일지정하지 않음!!"
    exit 0
fi

if [ -z "$CMD" ]; then
    usage "상태) BIN 파일지정하지 않음!!"
    exit 0
fi

if [ -z "$TARGETPATH" ]; then
    echo "상태) PATH 지정하지 않아. 현 위치와 동일한 위치에서 실행!!"
    TARGETPATH=`pwd`
fi

echo "실행서버 = [$SERVER]"
echo "실행명령 = [$CMD]"
echo "실행위치 = [$TARGETPATH]"

IFS=','
for x in $SERVER
do
    echo "ssh -t $GROUP$x \"bash -lci 'cd ${TARGETPATH} && ${CMD}'\""
    ssh -t $GROUP$x "bash -lci 'cd ${TARGETPATH} && ${CMD}'"
done
