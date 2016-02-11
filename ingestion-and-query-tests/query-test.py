#!/usr/bin/env python

import sys
import configparser
import platform
from subprocess import call
import datetime

def main(args=None):
    
    datetimeformatted = datetime.datetime.now().strftime('%Y%m%d%H%M')
    config = configparser.ConfigParser()
    config.sections()
    
    system = platform.system().lower()

    if system == 'windows':
        config.read('query-config-win.ini')
    else:
        config.read('query-config-nix.ini')

    seqn = 0
    pathreports = config['RUN']['pathreports']
    jmx = config['RUN']['jmx']
    machines = config['RUN']['machines'].split(",")
    reports = config['RUN']['reports'].split(",")
    tests = config['RUN']['tests'].split(",")
    properties = config['RUN']['properties']

    for test in tests:
        testName = datetimeformatted + "-%s" % test
        print(testName)
        command=  "-n -r -t " + jmx
        for machineidx,machine in enumerate(machines):
            command = command + " -Jm%s=%s" %(machineidx, machine)

        seqn += 1;
        for reportidx, report in enumerate(reports):
            command = command + " -Jreport%s=%s%s/%s-%s-%s" % (reportidx, pathreports, testName, testName, report, seqn)
        
        command= command + " -G%s -Gindexname=%s" %(properties, test)

        if config.getboolean('DEFAULT','debug'):
            print(command)
            print('\n')
        
        commandArgs = command.split(' ')

        if system == 'windows':
            print('running on windows')
            call(["jmeter.bat"] + commandArgs)
        else:
            print('running on linux')
            call(["jmeter"] + commandArgs)
            

if __name__ == "__main__":
    main()