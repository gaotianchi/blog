from datetime import datetime

import pytz

# 北京时区
beijing_timezone = pytz.timezone("Asia/Shanghai")

# 获取北京的当前时间
current_time_beijing = datetime.now(beijing_timezone).strftime("%Y-%m-%d %H:%M %z")

# 打印结果
print("北京的当前时间:", current_time_beijing)
