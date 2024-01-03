# Apache
Apache contains a very well working WebDAV implementation. This can be used as
a reference. For better reverse engineering, the keep-alive is deactivated.
This is somewhat cumbersome in Spring-Boot, which is why Apache is also used as
a reverse proxy for Spring-Boot and prevents the keep-alive in this way.

# Installation

## Apache
- Download Apache: https://www.apachelounge.com/download/  
  32 bit version is enough
- Unpack Apache to: ./apache  
  following directory structure should be created: ./apache/bin, ./apache/conf, ...
- Open a terminal and go to ./apache
- Start Apache via start.cmd

## SmartSniff
- Download SmartSniff: https://www.nirsoft.net/utils/smsniff.html
- After starting and before capturing, configure the capture filter  
  Options - Capture Filter (Ctrl + F9)  
  include:both:tcp:8000
