package com.fullerton.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.fullerton.model.Post;

@Controller
@RequestMapping("main")
public class MainController {
	private Map<Integer, Post> postsDatabase = new LinkedHashMap<>();
	private static final Logger log = LogManager.getLogger();
	private volatile int POST_ID_SEQUENCE = 1;
	
	@RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String list(Map<String, Object> model)
    {
        log.debug("Listing posts.");
        model.put("postDatabase", this.postsDatabase);

        return "main/list";
    }
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Map<String, Object> model,HttpSession session)
    {
		if(session.getAttribute("username") == null)
			return "login";
        model.put("postForm", new Post());
        return "main/add";
    }
	@RequestMapping(value = "create", method = RequestMethod.POST)
    public ModelAndView create(HttpSession session, @Valid @ModelAttribute("postForm") Post post,
            BindingResult result) throws IOException
    {
		if (result.hasErrors()) {
			return new ModelAndView("/main/add");
        }
        Post p = new Post();
        p.setTitle(post.getTitle());
        p.setName((String)session.getAttribute("username"));
        p.setDescription(post.getDescription());
        p.setLink(post.getLink());
        p.setVotes(0);
        this.postsDatabase.put(this.getNextTicketId(), p);

        return this.getListRedirectModelAndView();
    }
	@RequestMapping(value = "view/{postId}", method = RequestMethod.GET)
    public ModelAndView view(Map<String, Object> model,
                             @PathVariable("postId") int postId)
    {
        Post p = this.postsDatabase.get(postId);
        model.put("postId", Integer.toString(postId));
        model.put("post", p);
        return new ModelAndView("main/view");
    }
	@RequestMapping(value="voteUp/{postId}",method = RequestMethod.GET)
	public View voteUp(Map<String, Object> model,
            @PathVariable("postId") int postId){
		Post p = this.postsDatabase.get(postId);
		p.setVotes(p.getVotes()+1);
		this.postsDatabase.put(postId, p);
		this.sortPosts();
		return new RedirectView("/main/list", true, false);
	}
	@RequestMapping(value="voteDown/{postId}",method = RequestMethod.GET)
	public View voteDown(Map<String, Object> model,
            @PathVariable("postId") int postId){
		Post p = this.postsDatabase.get(postId);
		if(p.getVotes()!=0)
			p.setVotes(p.getVotes()-1);
		this.postsDatabase.put(postId, p);
		this.sortPosts();
		return new RedirectView("/main/list", true, false);
	}
	private synchronized int getNextTicketId()
    {
        return this.POST_ID_SEQUENCE++;
    }
	
	private void sortPosts() {
		List<Map.Entry<Integer, Post>> list = 
			new LinkedList<Map.Entry<Integer, Post>>(postsDatabase.entrySet());
 
		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<Integer, Post>>() {
			public int compare(Map.Entry<Integer, Post> o1,
                                           Map.Entry<Integer, Post> o2) {
				return (Integer.toString(o2.getValue().getVotes())).compareTo(Integer.toString(o1.getValue().getVotes()));
			}
		});
 
		// Convert sorted map back to a Map
		Map<Integer, Post> sortedMap = new LinkedHashMap<Integer, Post>();
		int count = 1;
		for (Iterator<Map.Entry<Integer, Post>> it = list.iterator(); it.hasNext();) {
			Map.Entry<Integer, Post> entry = it.next();
			sortedMap.put(count, entry.getValue());
			count++;
		}
		this.postsDatabase =  sortedMap;
	
	}
	private ModelAndView getListRedirectModelAndView()
    {
        return new ModelAndView(this.getListRedirectView());
    }

    private View getListRedirectView()
    {
        return new RedirectView("/main/list", true, false);
    }
}
