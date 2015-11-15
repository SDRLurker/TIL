#!/bin/bash
EXECUTE=`basename $0`
LIMIT=2000000000        # 파일이 LIMIT이 넘으면 mv됨.

#사용법 함수
function usage {
    echo "-----------------------------------------------------------------------"
    echo "사용방법) $EXECUTE 파일"
    echo "-----------------------------------------------------------------------"
    echo "파일이 $LIMIT 바이트를 넘었을 경우 그 파일을 파일.시분 으로 mv 합니다. "
    echo "-----------------------------------------------------------------------"
}

if [ "$#" -eq 1 ]; then
    NOW=`date '+%H%M'`
    BASEPARAM=`basename $1`
    # $1이 '/'를 포함하지 않으면 현재 디렉터리 파일을 사용
    if [ "$1" = "$BASEPARAM" ]; then
        TARGET=`pwd`/$1
    # $1이 '/'를 포함하면 절대경로로 파일을 사용
    else
        TARGET=$1
    fi
else
    usage
    exit 1;
fi

while true;
do
    # 현재 파일이 있는지 확인
    if [ -f ${TARGET} ]; then
        SIZE=`ls -lrt ${TARGET} | awk '{print $5}'`
        echo $SIZE
        if [ "$SIZE" -ge "$LIMIT" ]; then   # 현재 파일이 LIMIT 바이트 이상이면
            NOW=`date '+%H%M'`
            mv ${TARGET} ${TARGET}.${NOW}
            # 부가기능 해당파일을 압축
            tar cvfz ${TARGET}.${NOW}.tar.gz ${TARGET}.${NOW}
            rm -rf ${TARGET}.${NOW}
        fi
    fi
    sleep 1
done