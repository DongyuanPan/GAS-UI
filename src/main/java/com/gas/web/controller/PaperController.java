package com.gas.web.controller;

import com.gas.web.entity.Paper;

import com.gas.web.service.IPaperService;
import com.gas.web.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/paper")
public class PaperController {

    private final IPaperService paperService;

    @Autowired
    public PaperController(IPaperService paperService) {
        this.paperService = paperService;
    }

    /**
     * 查询所有论文列表
     *
     * @return
     */
    @GetMapping("")
    public Map<String, Object> paperFindAll() {
        return paperService.paperFindAll();
    }

    /**
     * 通过 id 查询论文
     * @return
     */
    @GetMapping("/{id}")
    public Response paperFindById(@PathVariable("id") Integer id) {
        Paper paper = null;
        try {
            paper = paperService.paperFindById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("查询失败", id);
        }
        return Response.success("查询成功", paper);
    }

    /**
     * 新增一个论文
     * @return
     */
    @PostMapping("/add")
    public Response paperAdd(@RequestParam("title") String title, @RequestParam("author1") String author1,
                              @RequestParam("author2") String author2, @RequestParam("authorOther") String authorOther,
                              @RequestParam("origin") String origin) {
        Paper paper = new Paper();
        paper.setTitle(title);
       paper.setAuthor1(author1);
       paper.setAuthor2(author2);
       paper.setAuthorOther(authorOther);
       paper.setOrigin(origin);
        //保存和更新都用该方法
        try {
            paperService.paperAdd(paper);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("添加失败", paper);
        }
        return Response.success("添加成功", paper);
    }


    /**
     * 通过 id 更新一个论文信息
     * @return
     */
    @PostMapping("/update/{id}")
    public Response paperUpdate(@PathVariable("id") Integer id,
                                @RequestParam("title") String title, @RequestParam("author1") String author1,
                                @RequestParam("author2") String author2, @RequestParam("authorOther") String authorOther,
                                @RequestParam("origin") String origin) {
        Paper paper = new Paper();
        paper.setId(id);
        paper.setTitle(title);
        paper.setAuthor1(author1);
        paper.setAuthor2(author2);
        paper.setAuthorOther(authorOther);
        paper.setOrigin(origin);
        //保存和更新都用该方法
        try {
            paperService.paperUpdate(paper);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("编辑失败", paper);
        }
        return Response.success("编辑成功", paper);
    }

    /**
     * 通过 id 删除一个学生
     *
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    public Response paperDelete(@PathVariable("id") Integer id) {
        try {
            paperService.paperDelete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("删除失败", id);
        }
        return Response.success("删除成功", id);
    }


    @DeleteMapping("/delete")
    public Response studentDeleteBatch(@RequestBody List<Paper> papList) {
        try {
            paperService.paperDeleteBatch(papList);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("删除失败", papList);
        }
        return Response.success("删除成功", papList);
    }

}
