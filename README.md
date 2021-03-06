# rtsp-crawler-engine
<br/><br/>The project contains two modules:
<br/>1. rtsp crawlers with an ability to probe and stream located RTSP (Real Time Streming Protocol), such as CCTV and so on. The crawlers have been written in python3.7 and they have been integrated with the masscan https://github.com/robertdavidgraham/masscan which is making possible for them to scan and probe a wide range of internet addresses by running a single command.
<br/>
<br/>2. a backend API to store and categorize probed streams. The API has been written in Java 1.8 / Spring framework. The crawlers could communicate automatically with a backend server while they are doing a long-ride probing.
<br/>
<br/>For more information, please use a --help argument on a crawler script. For the backend API endpoints, please refer to the end of this document.

# There are different ways to obtain the cameras:
# 1. blindly scan the internet and locate open streams:
<h3>REMEMBER! If you use masscan to locate the streams, please note that masscan uses it's own TCP/IP stack, so it will simply ignore your proxy and leak your source IP address.</h3>

./masscan_to_rtsp.py --masscan 10.0.0.0/8 --output masscan_results.txt
# 1.1 do an aggressive scan:
./masscan_to_rtsp.py --masscan 10.0.0.0/8 --aggressive --output masscan_results.txt

<h2> 2. IF YOU ARE A SHODAN MEMBER: Get the cameras from the Shodan API and feed them into the crawler. You must be a member of Shodan to export their results.</h2>

<h2> 1.2 consume the shodan JSON export to extract and probe cameras: </h2>
torify ./rtsp_probe.py --batch-json shodan-export.json --output probe_result.json

# 2 probe the results
torify ./rtsp_probe.py --batch-list masscan_results.txt --output probe_result.json
<br/>Use a tor proxy to keep yourself anonymous during the rtsp probing.



# 3 import retrieved cameras to the backend API:
torify ./rtsp_probe.py --batch-list masscan_results.txt --output probe_result.json --import http://localhost/cameras/import
<br/>where http://localhost/cameras/import is the URL to your backend API

# You can use rtsp_probe.py to continuously stream a single camera:
torify ./rtsp_probe.py --url rtsp://10.10.10.10:554 --stream

</br></br>
#Backend API:
</br><b>Use this URL with a rtsp prober to store the results</b>
</br>POST /cameras/import
</br>{
</br>  "status": "UNCONNECTED";
</br>  "url": "rtsp://10.10.10.10:554";
</br>}
</br>
</br>GET /cameras/?id=1
</br>{
</br>  "cameraId": 1,
</br>  "rtspUrl": "rtsp://82.209.221.80:554",
</br>  "creationTimestamp": "2019-00-03 12:00:00",
</br>  "lastUpdateTimestamp": "2019-03-14 07:03:02",
</br>  "status": "OPEN",
</br>  "countryName": null,
</br>  "city": null
</br>}

</br>
</br>GET /cameras/?rtspUrl="rtsp://82.209.221.80:554"
</br>{
  </br>"cameraId": 1,
  </br>"rtspUrl": "rtsp://82.209.221.80:554",
  </br>"creationTimestamp": "2019-00-03 12:00:00",
  </br>"lastUpdateTimestamp": "2019-03-14 07:03:02",
  </br>"status": "OPEN",
  </br>"countryName": null,
  </br>"city": null
</br>}

