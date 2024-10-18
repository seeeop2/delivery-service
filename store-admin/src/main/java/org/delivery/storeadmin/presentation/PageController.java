package org.delivery.storeadmin.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("")
@Controller
public class PageController {

    // 메인 페이지 요청을 처리하는 메서드
    // 루트 경로와 "/main" 경로에 대한 요청 매핑
    @RequestMapping(path = {"","/main"})
    public ModelAndView main(){
        // "main" 뷰를 반환하는 모델과 뷰 객체 생성
        return new ModelAndView("main");
    }

}
