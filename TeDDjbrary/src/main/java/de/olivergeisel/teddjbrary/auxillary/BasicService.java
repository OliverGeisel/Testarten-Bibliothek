/*
 * Copyright 2023 Oliver Geisel
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.olivergeisel.teddjbrary.auxillary;

import jakarta.persistence.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

interface PostRepository extends CrudRepository<Post, Long> {

}

@Service
public class BasicService {
	private final PostRepository postRepository;

	public BasicService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public Post createPost(String content, String from, String role) {
		return postRepository.save(new Post(content, from, role));
	}

	public Post addPost(Post post) {
		return postRepository.save(post);
	}

	public void deletePost(Long id) {
		postRepository.deleteById(id);
	}

	//region setter/getter
	public Iterable<Post> getAllPosts() {
		return postRepository.findAll();
	}
//endregion
}

@Entity
class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true)
	private Long   id;
	@Column(length = 10_000)
	private String content;
	private String from;
	private String role;

	public Post(String content, String from, String role) {
		this.content = content;
		this.from = from;
		this.role = role;
	}

	public Post() {

	}

	//region setter/getter
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}
//endregion

}
