issues:
user.setCreateUserID(auths.getUser().getID()); 管理员参与更新时，生成的UserID从属问题
所以长期看，不能按照CreateUserID来控制从属，应该专门的设计一张表来映射父子用户

无权限访问
有权限访问，无权限操作
有权限访问，有权限操作
有权限访问，有权限操作，没有特定的业务权限（这个权限需要代码逻辑来控制）

bmsauth权限单位
bmsauthmapping 菜单、权限单位各自可以自定义的URL地址映射
bmsgroup 分组
bmsgroupauth 分组对应的权限单位，最小粒度分组权限
bmsgroupmenu 分组对应的菜单显示信息
bmsmenu 菜单
bmsmenuauth 菜单对应的权限集合信息，仅用于页面显示、勾选操作
bmsuser 用户
bmsusergroup 用户所从属的分组

权限粒度的大小，可以自定义，比如用户管理可以包含查看、编辑，也可以作为一个大的权限单位映射不同的URL

如：/user 可在该节点定义大类权限，覆盖以下的所有权限
/user/view
/user/add
/user/delete

1个菜单对应一系列处理流程，可有多个页面，每个页面可有多个权限元素
没配置的URL放行

权限包含：
1、菜单显示权限
2、流程处理涉及的一系列页面权限
3、按钮点击类相关权限
4、管理员权限
      注解@Admin的仅Admin可以操作，不可以通过授权来委托

      支持菜单级权限控制，也支持页面级权限控制，可分开或结合起来实施
