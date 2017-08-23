package bms.core.common;

/**
 * @author xu.jian
 * 
 */
public class Consts {
	public class Path {
		// to view
		public static final String error = "error";
		public static final String home = "home";
		public static final String welcome = "welcome";
		public static final String group = "group";
		public static final String groupuser = "groupuser";
		public static final String usergroup = "usergroup";
		public static final String menuauth = "menuauth";
		public static final String groupmenu = "groupmenu";
		public static final String menu = "menu";
		public static final String user = "user";
		public static final String login = "login";
		public static final String permission = "permission";
		public static final String auth = "auth";
		public static final String urlset = "urlset";

		// to controller
		public static final String R_Error = "redirect:/mgr/error";
		public static final String R_Home = "redirect:/mgr/home";
		public static final String R_User_List = "redirect:/mgr/user/list";
	}

	public class Messages {
		public static final String User_Add = "fail.user.add";
		public static final String User_Login = "fail.user.login";
		public static final String AUTH_NO_AUTH = "auth.noauth";
		public static final String MONITOR_NULL = "monitor.null";
		public static final String MONITOR_EMPTY = "monitor.empty";
	}

	public class Keys {
		public static final String SESSION_USER = "session_user";
		public static final String SESSION_AUTHS = "session_auth";
		public static final String SESSION_AUTH_IDS = "session_auth_id";
		public static final String PATH_PREFIX = "/mgr";
		public static final String SITE_PREFIX = "/monitor";
	}

	public class Values {
		public static final String SPLITER = ",";
	}
}
