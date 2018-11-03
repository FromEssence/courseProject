import requests
import os, sys
import json
import urllib

url_scan = 'https://www.virustotal.com/vtapi/v2/file/scan'
params_scan = {'apikey': ''}

url_report = 'https://www.virustotal.com/vtapi/v2/file/report'
params_report = {'apikey': '', 'resource': 'f2fb6483265b5f6a37ada509b478806da9f4bcb88ced834e40946bd32a755d37-1540392588'}


def func(path):

    if(os.path.exists(path) == False):
        return
    
    files = {'file': (path, open(path, 'rb'))}
    #with open(path, 'rb') as files:
    response_scan = requests.post(url_scan, files=files, params=params_scan)

    print(response_scan.json())

    response_report = requests.get(url_report, params=params_report)

    res = response_report.json()
    #print(response.json())
    #Kaspersky McAfee Qihoo-360
    print(res['scans']['Bkav'])

    
    with open('report.txt', 'w') as outfile:
        #json.dump(response.json(), outfile)
        outfile.write('\n可疑程序'+path+'扫描结果：\n')
        outfile.write('Kaspersky:')
        if str(res['scans']['Kaspersky']['result'])== 'None':
            outfile.write('程序安全\n')
        else:
            
            outfile.write(str(res['scans']['Kaspersky']['result'])+'\n')
            

        outfile.write('McAfee:')
        if str(res['scans']['McAfee']['result'])== 'None':
            outfile.write('程序安全\n')
        else:
            outfile.write(str(res['scans']['McAfee']['result'])+'\n')

        outfile.write('Qihoo-360:')
        if str(res['scans']['Qihoo-360']['result'])== 'None':
            outfile.write('程序安全\n')
        else:
            outfile.write(str(res['scans']['Qihoo-360']['result'])+'\n')

        if(str(res['scans']['Kaspersky']['result'])!= 'None' or str(res['scans']['McAfee']['result'])!= 'None' or str(res['scans']['Qihoo-360']['result'])!= 'None':
           os.remove(path)
           outfile.write('恶意程序'+path+'已清除！\n')
        
        
if __name__ == '__main__':
    print(sys.argv)
    if(os.path.exists("report.txt")):
        os.remove("report.txt")
    for i in range(1, len(sys.argv)):
        virusPath = sys.argv[i]
        print(virusPath)
        func(virusPath)
