#!/bin/bash

# 修改机器名称
hostnamectl set-hostname hadoop221
hostnamectl set-hostname hadoop222
hostnamectl set-hostname hadoop223


# 批量修改HOSTS
echo '

192.168.0.221 hadoop221
192.168.0.222 hadoop222
192.168.0.223 hadoop223

' >> /etc/hosts 


