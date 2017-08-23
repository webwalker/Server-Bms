package bms.core.common.util;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;

import bms.core.common.Consts;
import bms.core.model.MyResponse;

/**
 * @author xu.jian
 * 
 */
public class MessageUtil {
	private ResourceBundleMessageSource messageSource;

	public MyResponse getSuccess() {
		String message = getMessage("success");
		return new MyResponse(true, message);
	}

	public MyResponse getFailed() {
		String message = getMessage("fail");
		return new MyResponse(false, message);
	}

	public MyResponse getFieldError(FieldError error) {
		String message = error.getDefaultMessage();
		return new MyResponse(false, message);
	}

	public String toError(Model model, String code) {
		String message = getMessage(code);
		model.addAttribute("msg", message);
		return Consts.Path.error;
	}

	public String toError(Model model, FieldError error) {
		String message = error.getDefaultMessage();
		model.addAttribute("msg", message);
		return Consts.Path.error;
	}

	public MyResponse getResponse(boolean success, String code) {
		String message = getMessage(code);
		return new MyResponse(success, message);
	}

	public String getMessage(String code) {
		try {
			String msg = messageSource.getMessage(code, null, "", Locale.CHINA);
			return msg != null ? msg.trim() : msg;
		} catch (Exception e) {
			return  "";
		}
	}

	public String getMessage(String code, Object[] args, Locale locale) {
		String msg = messageSource.getMessage(code, args, "", locale);
		return msg != null ? msg.trim() : msg;
	}

	public String getMessage(String code, Object[] args, String defaultMessage,
			Locale locale) {
		String msg = messageSource.getMessage(code, args, defaultMessage,
				locale);
		return msg != null ? msg.trim() : msg;
	}

	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
