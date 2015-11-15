YY=`date +%y`
MM=`date +%m`
DD=`date +%d`

EXECUTE=`basename $0`
SERVER=$1
CPFILES=$2
CPPATH=$3
GROUP="GROUP"	# 넘길서버의 동일한 부분의 도메인이나 IP를 GROUP 자리에 입력

#사용법 함수
usage()
{

local MESSAGE=$1
echo "-----------------------------------------------------------------------"
echo "사용방법 1) $EXECUTE 넘길서버 넘길파일 [넘길위치]"
echo "-----------------------------------------------------------------------"
echo "사용방법 2) $EXECUTE 넘길서버 넘길파일"
echo "            [넘길위치]를 지정하지 않으면, pwd와 동일 위치로 복사"
echo "-----------------------------------------------------------------------"
echo "넘길서버) 쉼표로 구분하여 그룹의 마지막 자리만 입력"
echo "          $EXECUTE 1,2,3,4"
echo "          => 현재서버에서 ${GROUP}1,...,${GROUP}4로 복사를 수행합니다."
echo "-----------------------------------------------------------------------"
echo $MESSAGE
echo "-----------------------------------------------------------------------"

}

if [ -z "$SERVER" ]; then
    usage "상태) SERVER 파일지정하지 않음!!"	
    exit 0
fi

if [ -z "$CPFILES" ]; then
    usage "상태) BIN 파일지정하지 않음!!"
    exit 0
fi

if [ -z "$CPPATH" ]; then
    echo "상태) PATH 지정하지 않아. 현 위치와 동일한 위치로 복사!!"
    CPPATH=`pwd`
fi

echo "넘길서버 = [$SERVER]"
echo "넘길파일 = [$CPFILES]"
echo "넘길위치 = [$CPPATH]"

if [ -f "$CPPATH/$CPFILES" ]; then
    IFS=','
    for x in $SERVER
    do
        echo "rsh $GROUP$x \"ls $CPPATH/$CPFILES | wc -l\""
        RESULT=`rsh $GROUP$x "ls $CPPATH/$CPFILES | wc -l"`
        if [ $RESULT -eq "1" ]; then 
            echo "rsh $GROUP$x \"cp -p $CPPATH/$CPFILES $CPPATH/$CPBIN.$YY$MM$DD\""
            rsh $GROUP$x "cp -p $CPPATH/$CPFILES $CPPATH/$CPBIN.$YY$MM$DD"
        fi
        echo "rcp -p $CPPATH/$CPFILES $GROUP$x:$CPPATH/$CPBIN"
        rcp -p $CPPATH/$CPFILES $GROUP$x:$CPPATH/$CPBIN
    done
fi
