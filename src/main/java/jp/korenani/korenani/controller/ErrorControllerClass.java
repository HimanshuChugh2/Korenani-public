//package jp.korenani.korenani.controller;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class ErrorControllerClass implements ErrorController{
//
//	//if the user tries to visit any other page which does not exist, then it will first ask the user to log in, this is not a mistake, its a feature, no drawback of this could be found, so leaving is as it is.
//	
//	@GetMapping("/error")
//	public String handlingErrorGeneral(HttpServletRequest request)
//	{
//		Object statusObject=request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//				if(statusObject!=null)
//				{
//					Integer statusCode=Integer.valueOf(statusObject.toString());
// 
//					if(statusCode==HttpStatus.NOT_FOUND.value())
//					{
// 						return "error-pages/error-404";
//					}
//					
//					
//					if(statusCode==HttpStatus.INTERNAL_SERVER_ERROR.value())
//					{					
//  						return "error-pages/error-500";
//					}
//					if(statusCode==HttpStatus.UNAUTHORIZED.value())
//					{
// 
//						return "/login";
//					}
//				}
//				return "/login";
//	}
//	
//	@Override
//	public String getErrorPath() {
//		// TODO Auto-generated method stub
//		return "/error";
//	}
//
//}
