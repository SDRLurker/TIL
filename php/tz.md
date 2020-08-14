출처  
[https://stackoverflow.com/questions/19074552/converting-utc-to-a-different-time-zone-in-php](https://stackoverflow.com/questions/19074552/converting-utc-to-a-different-time-zone-in-php)

## PHP에서 UTC 시간 대를 다른 시간 대로 변환하기

저는 UTC 시간대를 다른 시간대로 변환하기 위해 아래의 메소드를 사용하였습니다. 하지만 아래의 메소드는 UTC 시간을 리턴하는 것으로 보입니다. 제가 사용한 메소드에 잘못된 것이 무엇인지 충분히 알려주실 수 있으신가요?

```
static function formatDateMerchantTimeZone($t, $tz) {
   if (isset($t)) {
       return date('Y-m-d H:i:s', strtotime($t , $tz));
   } else {
       return null;
   }
}
```

$t는 제가 전달한 datetime입니다.  
$tz는 America/Los\_Angeles 같은 시간대 입니다.

---

### 4개의 답변 중 1개의 답변만 추려냄

다음을 시도해보세요.

```
    <?php
/**    초 단위로 origin 시간대에서 remote 시간대의 offset을 리턴합니다..
*    @param $remote_tz;
*    @param $origin_tz; null 이면 서버의 현재 시간대가 origin 으로 사용됩니다.
*    @return int;
*/
function get_timezone_offset($remote_tz, $origin_tz = null) {
    if($origin_tz === null) {
        if(!is_string($origin_tz = date_default_timezone_get())) {
            return false; // A UTC timestamp was returned -- bail out!
        }
    }
    $origin_dtz = new DateTimeZone($origin_tz);
    $remote_dtz = new DateTimeZone($remote_tz);
    $origin_dt = new DateTime("now", $origin_dtz);
    $remote_dt = new DateTime("now", $remote_dtz);
    $offset = $origin_dtz->getOffset($origin_dt) - $remote_dtz->getOffset($remote_dt);
    return $offset;
}
?>
Examples:
<?php
// 이는 10800을 리턴합니다 (3시간) ...
$offset = get_timezone_offset('America/Los_Angeles','America/New_York');
// 또는 이미 서버 시간이 'America/New_York'로 설정되어 있다면...
$offset = get_timezone_offset('America/Los_Angeles');
// $offset 값을 취해서 당신의 timestamp 값을 조절할 수 있습니다.
$offset_time = time() + $offset;
?>
```
