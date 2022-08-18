package com.atguigu.crud.controller;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.Msg;
import com.atguigu.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理员工CRUD请求
 */
@Controller
public class EmployeeController {






    @Autowired
    EmployeeService employeeService;

    /**
     * @作者: zhulinjia
     * @时间: 2022/8/18 14:25
     * @Return:
     * @Trans:
     * 更新员工信息
     */
    @ResponseBody
    @RequestMapping(value="/emp/{empId}",method=RequestMethod.PUT)
    public Msg saveEmp(Employee employee, HttpServletRequest request){
        System.out.println("将要更新的员工数据："+employee);
        employeeService.updateEmp(employee);
        return Msg.success();
    }


    /**
     * @作者: zhulinjia
     * @时间: 2022/8/18 10:14
     * @Return:
     * @Trans:
     * 查询员工name的方法
     */
    @RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id")Integer id){

        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp", employee);
    }






    /**
     * @作者: zhulinjia
     * @时间: 2022/8/16 14:38
     * @Return:
     * @Trans:
     */

    /*@RequestMapping("/checkuser")
    @ResponseBody
    public Msg checkUser(@RequestParam("empName") String empName) {
        // 先判断用户名是否是合法的表达式
        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        // 检查正则表达式是否匹配
         if(!empName.matches(regx)){
              return Msg.fail().add("va_msg","用户名必须是6-16位数字和字母的组合");
         }

        boolean b = employeeService.checkUser(empName);

        if(b){
            return Msg.success();
        }else{
            return Msg.fail().add("va_msg","用户名不可用");
        }

    }*/


    @ResponseBody
    @RequestMapping("/checkuser")
    public Msg checkuser(@RequestParam("empName")String empName){
        //先判断用户名是否是合法的表达式;
        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
        if(!empName.matches(regx)){
            return Msg.fail().add("va_msg", "用户名必须是6-16位数字和字母的组合或者2-5位中文");
        }

        //数据库用户名重复校验
        boolean b = employeeService.checkUser(empName);
        if(b){
            return Msg.success();
        }else{
            return Msg.fail().add("va_msg", "用户名不可用");
        }
    }



    //员工保存
    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @ResponseBody
    // 页面传输的数据和参数一致的情况下会自动封装

    //使用JSR303，员工保存的时候，使用@Valid,需要绑定bindingResult才能获取到校验失败的结果result
    public Msg saveEmp(@Valid  Employee employee, BindingResult result) {
        if (result.hasErrors()){
            //封装错误信息
            Map<String,Object> map = new HashMap<>();

            //校验失败应该返回失败,在模态框当中，显示校验错误信息
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError f:
                    fieldErrors) {
                System.out.println("错误的字段名"+f.getField());
                System.out.println("错误信息"+f.getDefaultMessage());
                map.put(f.getField(),f.getDefaultMessage());
            }

            return Msg.fail().add("errorFields",map);
        }else {
            employeeService.saveEmp(employee);

            return Msg.success();
        }


    }


    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
                              Model model) {

        // 这不是一个分页查询；
        // 引入PageHelper分页插件
        // 在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        // startPage后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
        // 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(emps, 5);
        return Msg.success().add("pageInfo", page);
    }


    /**
     * 查询员工数据（分页查询）
     *
     * @return
     */
    // @RequestMapping("/emps")
    public String getEmps(
            @RequestParam(value = "pn", defaultValue = "1") Integer pn,
            Model model) {
        // 这不是一个分页查询；
        // 引入PageHelper分页插件
        // 在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        // startPage后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
        // 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(emps, 5);
        model.addAttribute("pageInfo", page);
        return "list";
    }


}
