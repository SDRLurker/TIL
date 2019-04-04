#!/bin/bash
EXECUTE=`basename $0`
SERVER=$1
FILE=$2
GROUP="10.101.32."  # 넘길서버의 동일한 부분의 도메인이나 IP를 GROUP 자리에 입력합니다.
TARGET=`pwd`
TARGET_PATH=$TARGET/$FILE

usage()
{

local MESSAGE=$1
echo "-----------------------------------------------------------------------"
echo "사용방법 1) $EXECUTE [원격서버] [비교할파일]"
echo "-----------------------------------------------------------------------"
echo [오류 내용] $MESSAGE
echo "-----------------------------------------------------------------------"

}

if [ -z "$SERVER" ]; then
    usage "원격 SERVER 지정하지 않음!!"
    exit 0
fi

if [ -z "$FILE" ]; then
    usage "비교할 파일 지정하지 않음!!"
    exit 0
fi

echo "diff $FILE <(ssh $GROUP$SERVER \"cat $TARGET_PATH\")"
diff $FILE <(ssh $GROUP$SERVER "cat $TARGET_PATH")
