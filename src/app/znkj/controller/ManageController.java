package app.znkj.controller;

import app.znkj.config.SessionInterceptor;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * 实验室管理--重定向控制器
 * @author xian.zf
 *
 * @date 2014-7-19
 */
@Before(SessionInterceptor.class)
public class ManageController extends Controller{

	public void index() throws Exception {
		render("lab.html");
	}
	
	public void lab() {
		render("lab.html");
	}
	
	public void editlab() {
		render("editlab.html");
	}
	
	public void labchief() {
		render("labchief.html");
	}

	public void committee() {
		render("committee.html");
	}

	public void direction() {
		render("direction.html");
	}

	public void equipment() {
		render("equipment.html");
	}

	public void useequ() {
		render("useequ.html");
	}
	
	public void news() {
		render("news.html");
	}

	public void poster() {
		render("poster.html");
	}

	public void notice() {
		render("notice.html");
	}

	public void annual() {
		render("annual.html");
	}

	public void team() {
		render("team.html");
	}

	public void rule() {
		render("rule.html");
	}

	public void userlist() {
		render("userlist.html");
	}

	public void lastachieve() {
		render("lastachieve.html");
	}
	
	public void recruit() {
		render("recruit.html");
	}

	/**
	 * 开放基金附件
	 * @author Lu.bl
	 * @date 2015-9-7
	 * @throws Exception
	 */
	public void attach() throws Exception{
		render("attach.html");
	}
	/**
	 * 网站链接
	 * @author Lu.bl
	 * @date 2015-9-7
	 * @throws Exception
	 */
	public void link() throws Exception{
		render("link.html");
	}
	/**
	 * 添加网站链接
	 * @author Lu.bl
	 * @date 2015-9-8
	 * @throws Exception
	 */
	public void addlink() throws Exception{
		render("add/addlink.html");
	}

	public void academic() {
		render("academic.html");
	}

	public void fund() {
		render("fund.html");
	}
	
	public void software() {
		render("software.html");
	}
	
	public void backups() {
		render("backups.html");
	}
	
	public void indexpic() {
		render("indexpic.html");
	}
	
	//新增、编辑、查看
	public void addindexpic() {
		render("add/addindexpic.html");
	}
	
	public void addsoftware() {
		render("add/addsoftware.html");
	}
	
	public void viewsoftware() {
		render("view/viewsoftware.html");
	}
	
	public void addacademic() {
		render("add/addacademic.html");
	}

	public void addfund() {
		render("add/addfund.html");
	}
	
	public void addchief() {
		render("add/addchief.html");
	}

	public void viewchief() {
		render("view/viewchief.html");
	}
	
	public void addcommittee() {
		render("add/addcommittee.html");
	}

	public void viewcommittee() {
		render("view/viewcommittee.html");
	}

	public void adddirection() {
		render("add/adddirection.html");
	}

	public void viewdirection() {
		render("view/viewdirection.html");
	}

	public void addteam() {
		render("add/addteam.html");
	}

	public void viewteam() {
		render("view/viewteam.html");
	}

	public void addequipment() {
		render("add/addequipment.html");
	}

	public void viewequipment() {
		render("view/viewequipment.html");
	}

	public void adduseequ() {
		render("add/adduseequ.html");
	}

	public void viewuseequ() {
		render("view/viewuseequ.html");
	}

	public void addnews() {
		render("add/addnews.html");
	}

	public void viewnews() {
		render("view/viewnews.html");
	}

	public void addannual() {
		render("add/addannual.html");
	}

	public void viewannual() {
		render("view/viewannual.html");
	}

	public void addposter() {
		render("add/addposter.html");
	}

	public void viewposter() {
		render("view/viewposter.html");
	}

	public void addnotice() {
		render("add/addnotice.html");
	}

	public void viewnotice() {
		render("view/viewnotice.html");
	}

	public void addrule() {
		render("add/addrule.html");
	}

	public void viewrule() {
		render("view/viewrule.html");
	}

	public void adduser() {
		render("add/adduser.html");
	}

	public void viewuser() {
		render("view/viewuser.html");
	}

	public void addachieve() {
		render("add/addachieve.html");
	}

	public void addrecruit() {
		render("add/addrecruit.html");
	}
	
	public void viewrecruit() {
		render("view/viewrecruit.html");
	}
	
	public void editaward() {
		render("add/editaward.html");
	}
	
	public void editconference() {
		render("add/editconference.html");
	}
	
	public void editpatent() {
		render("add/editpatent.html");
	}
	
	public void editperiodical() {
		render("add/editperiodical.html");
	}
	
	public void editthesis() {
		render("add/editthesis.html");
	}
	
	public void edittreatise() {
		render("add/edittreatise.html");
	}
	
	public void editproject() {
		render("add/editproject.html");
	}
	
	
	/***科研管理***/
	
	public void award() {
		render("research/award.html");
	}
	
	public void conference() {
		render("research/conference.html");
	}
	
	public void patent() {
		render("research/patent.html");
	}
	
	public void periodical() {
		render("research/periodical.html");
	}
	
	public void project() {
		render("research/project.html");
	}
	
	public void thesis() {
		render("research/thesis.html");
	}
	
	public void treatise() {
		render("research/treatise.html");
	}
	
	/**
	 * 新增
	 */
	
	public void addaward() {
		render("research/add/addaward.html");
	}
	
	public void addconference() {
		render("research/add/addconference.html");
	}
		
	public void addpatent() {
		render("research/add/addpatent.html");
	}
	public void addthesis() {
		render("research/add/addthesis.html");
	}
	
	public void addperiodical() {
		render("research/add/addperiodical.html");
	}

	public void addproject() {
		render("research/add/addproject.html");
	}
	
	/**
	 * 查看
	 */
	public void viewacademic() {
		render("view/viewacademic.html");
	}
	
	public void viewaward() {
		render("research/view/viewaward.html");
	}
	
	public void viewconference() {
		render("research/view/viewconference.html");
	}
	
	public void viewfund() {
		render("view/viewfund.html");
	}
	
	public void viewpatent() {
		render("research/view/viewpatent.html");
	}
	public void viewthesis() {
		render("research/view/viewthesis.html");
	}
	
	public void viewperiodical() {
		render("research/view/viewperiodical.html");
	}

	public void viewproject() {
		render("research/view/viewproject.html");
	}
	
	public void viewtreatise() {
		render("research/view/viewtreatise.html");
	}
	
}
