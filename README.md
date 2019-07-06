# rtsp-crawler-engine

<h3>Use a tor proxy to keep yourself anonymous during the rtsp probing.</h3>
service tor start

# There are different ways to obtain the cameras:
# 1. blindly scan the internet and locate open streams:
<h3>REMEMBER! If you are using a masscan to locate the streams, please note that masscan uses it's own TCP/IP stack, so it will ignore your proxy. To avoid this and keep your anonymity safe, you can try to init your scans via a VPN connection with packets masquerading.</h3>

./masscan_to_rtsp.py --masscan 10.0.0.0/8 --output masscan_results.txt
# 1.1 do an aggressive scan:
./masscan_to_rtsp.py --masscan 10.0.0.0/8 --aggressive --output masscan_results.txt

<h2> 2. Get the cameras from the Shodan API and feed them into the crawler. You should be a member of Shodan to export their results.</h2>

<h2> 1.2 consume the shodan JSON export to extract and probe cameras: </h2>
torify ./rtsp_probe.py --batch-json shodan-export.json --output probe_result.json

# 2 probe the results
torify ./rtsp_probe.py --batch-list masscan_results.txt --output probe_result.json



# 3 stream retrieved cameras to the backend API:
torify ./rtsp_probe.py --batch-list masscan_results.txt --output probe_result.json --import http://localhost/camera/stream


# You can use rtsp_probe.py to continuously stream a single camera:
torify ./rtsp_probe.py --url rtsp://10.10.10.10:554 --stream

</br></br>
#Backend API:
</br><b>Use this URL with a rtsp prober to store the results</b>
</br>POST /cameras/import
</br>{
</br> "@timestamp": "yyyy-mm-dd hh:mm:ss"
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
