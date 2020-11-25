# 介绍
监听系统息屏广播开启一个自己的背景透明的activity以实现自定义锁屏

# 注意
activity需要添加flag：<br>
FLAG_DISMISS_KEYGUARD（去掉锁屏界面，但这对安全锁（图案或者密码锁屏界面）是无效的）<br>
FLAG_SHOW_WHEN_LOCKED（使得窗口浮在锁屏界面之上）<br><br>
android Q以上需要悬浮窗权限<br>
小米手机需要后台弹出权限和锁屏显示权限<br>
酷派手机需要将本应用设置为受保护应用<br>
