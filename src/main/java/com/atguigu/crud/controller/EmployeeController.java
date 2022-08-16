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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 处理员工CRUD请求
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    //员工保存
    @RequestMapping(value = "/emp",method = RequestMethod.POST)
    @ResponseBody
    // 页面传输的数据和参数一致的情况下会自动封装
    public Msg saveEmp(Employee employee){
        employeeService.saveEmp(employee);

        return Msg.success();
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
       return Msg.success().add("pageInfo",page);
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
