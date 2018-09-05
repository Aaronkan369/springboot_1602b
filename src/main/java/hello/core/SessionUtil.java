package hello.core;

import javax.servlet.http.HttpSession;

import hello.user.Stu;

/**对话工具
 * @author wc
 *
 */
public class SessionUtil {
	private static final String USER_KEY = "USER_KEY";
	public static  void setUser(HttpSession httpSession, Stu stu) {
		httpSession.setAttribute(USER_KEY, stu);
	}
	public static  Stu getUser(HttpSession httpSession) {
		return (Stu) httpSession.getAttribute(USER_KEY);
	}
}
