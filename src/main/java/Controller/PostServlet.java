package Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.hibernate.Session;
import org.hibernate.Transaction;

import HibernateUtil.HibernateUtil;
import Model.Post;

@WebServlet("/posting")
public class PostServlet extends HttpServlet {
	private static final String UPLOAD_DIRECTORY = "uploads";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Post> posts = session.createQuery("from Post", Post.class).list();
		session.close();
		req.setAttribute("posts", posts);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String content = req.getParameter("content");
		Part filePart = req.getPart("image");

		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists())
			uploadDir.mkdir();

		filePart.write(uploadPath + File.separator + fileName);

		Post post = new Post();
		post.setPostContent(content);
		post.setPostImage(UPLOAD_DIRECTORY + "/" + fileName);

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.save(post);
		transaction.commit();
		session.close();

		resp.sendRedirect("post");
	}
}
