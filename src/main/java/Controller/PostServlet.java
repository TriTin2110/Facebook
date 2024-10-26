package Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.Gson;

import DAO.PostDAO;
import HibernateUtil.HibernateUtil;
import Model.Post;
import Model.User;

@WebServlet("/posting")
@MultipartConfig
public class PostServlet extends HttpServlet {
	private static final String UPLOAD_DIRECTORY = "uploads";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			System.out.println(1);
			PostDAO postDAO = new PostDAO();
			System.out.println(2);
			List<Post> posts = postDAO.getPostByDateDesc();
			System.out.println(3);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			Gson gson = new Gson();
			String json = gson.toJson(posts);
			System.out.println(4);
			resp.getWriter().write(json);
			System.out.println(5);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving posts");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String content = req.getParameter("content");
		Part filePart = req.getPart("image");
		User currentUser = (User) req.getSession().getAttribute("user");

		if (currentUser == null) {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
			return;
		}

		Post post = new Post();
		post.setPostId(UUID.randomUUID().toString());
		post.setPostContent(content);
		post.setUser(currentUser);
		post.setCreatedAt(new Date());

		if (filePart != null && filePart.getSize() > 0) {
			String fileName = UUID.randomUUID().toString() + "_"
					+ Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}

			filePart.write(uploadPath + File.separator + fileName);
			post.setPostImage(req.getContextPath() + "/" + UPLOAD_DIRECTORY + "/" + fileName);
		}

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();
			session.save(post);
			transaction.commit();

			// Export posts to SQL file
			PostDAO postDAO = new PostDAO();
			String sqlFilePath = getServletContext().getRealPath("/WEB-INF/facebook_post.sql");
			postDAO.exportPostsToSQL(sqlFilePath);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error saving post");
			return;
		}

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		String json = gson.toJson(post);
		resp.getWriter().write(json);
	}
}
