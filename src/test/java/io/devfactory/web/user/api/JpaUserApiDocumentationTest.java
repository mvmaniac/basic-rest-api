package io.devfactory.web.user.api;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import io.devfactory.global.config.RestDocsConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.restdocs.test.autoconfigure.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(RestDocsConfig.class)
@TestConstructor(autowireMode = ALL)
@SpringBootTest
class JpaUserApiDocumentationTest {

  private final MockMvc mockMvc;

  @DisplayName("사용자 전체 목록 조회하기")
  @Test
  void retrieveAllUsers() throws Exception {
    // rest-doc
    // 테스트가 성공하면 build/generated-snippets 아래에 adoc 파일들이 생김
    // 생긴 adoc 파일들을 src/docs/asciidoc 경로 아래에 하나의 파일로 작성(?)
    // 작성된 하나의 adoc 파일을 html로 변환시키고 해당 html 파일을 서버 리소스를 통해 올림
    // @formatter:off
    mockMvc.perform(
        RestDocumentationRequestBuilders.get("/jpa/users")
            .contentType(APPLICATION_JSON)
            .with(httpBasic("dev", "1234"))
        )
        .andExpect(status().isOk())
        .andDo(MockMvcRestDocumentation.document("retrieveAllUsers",
            requestHeaders(
                headerWithName("Authorization").description("Basic auth credentials")
            ),
            responseFields(
                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("아이디"),
                fieldWithPath("[].name").type(JsonFieldType.STRING).description("사용자 이름"),
                fieldWithPath("[].joinDate").type(JsonFieldType.STRING).description("사용자 등록일"),
                fieldWithPath("[].ssn").type(JsonFieldType.STRING).description("사용자 주민번호"),
                fieldWithPath("[].posts").type(JsonFieldType.ARRAY).description("사용자 포스트 목록"),
                fieldWithPath("[].posts.[].id").type(JsonFieldType.NUMBER).description("사용자 포스트 아이디"),
                fieldWithPath("[].posts.[].description").type(JsonFieldType.STRING).description("사용자 포스트 내용")
            )
        ))
        .andDo(print())
    ;
    // @formatter:on

    // rest-doc + open api 3
    // openapi3 태스크로 생성된 build/api-spec 아래에 yml 파일을
    // 서버 리소스틀 통해 올리고 swagger-ui의 index.html 파일의 url를 수정하여 불러 올 수 있음
    // swagger를 docker로 별도로 띄우면 여러개의 URL를 지정하여 볼 수 있음
    // @formatter:off
    mockMvc.perform(
        RestDocumentationRequestBuilders.get("/jpa/users")
            .contentType(APPLICATION_JSON)
            .with(httpBasic("dev", "1234"))
        )
      .andExpect(status().isOk())
      .andDo(MockMvcRestDocumentationWrapper.document("retrieveAllUsers",
          resource(
              ResourceSnippetParameters.builder()
                  .description("사용자 전체 목록 조회하기")
                  .summary("사용자 전체 목록")
                  .requestHeaders(
                      headerWithName("Authorization").description("Basic auth credentials")
                  )
                  .responseFields(
                      fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("아이디"),
                      fieldWithPath("[].name").type(JsonFieldType.STRING).description("사용자 이름"),
                      fieldWithPath("[].joinDate").type(JsonFieldType.STRING).description("사용자 등록일"),
                      fieldWithPath("[].ssn").type(JsonFieldType.STRING).description("사용자 주민번호"),
                      fieldWithPath("[].posts").type(JsonFieldType.ARRAY).description("사용자 포스트 목록"),
                      fieldWithPath("[].posts.[].id").type(JsonFieldType.NUMBER).description("사용자 포스트 아이디"),
                      fieldWithPath("[].posts.[].description").type(JsonFieldType.STRING).description("사용자 포스트 내용")
                  )
                  .build()
          )
      ))
      .andDo(print())
    ;
    // @formatter:on
  }

  @WithMockUser
  @DisplayName("특정 사용자 조회하기")
  @Test
  void retrieveUser() throws Exception {
    // rest-doc
    // @formatter:off
    mockMvc.perform(
        RestDocumentationRequestBuilders.get("/jpa/users/{id}", 1)
            .contentType(APPLICATION_JSON)
            .with(httpBasic("dev", "1234"))
        )
        .andExpect(status().isOk())
        .andDo(MockMvcRestDocumentation.document("retrieveUser",
            requestHeaders(
                headerWithName("Authorization").description("Basic auth credentials")
            ),
            pathParameters(
                parameterWithName("id").description("사용자 아이디")
            ),
            links(
                linkWithRel("all-users").description("사용자 전체 목록 조회하기")
            ),
            responseFields(
                fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
                fieldWithPath("joinDate").type(JsonFieldType.STRING).description("사용자 등록일"),
                fieldWithPath("ssn").type(JsonFieldType.STRING).description("사용자 주민번호"),
                fieldWithPath("_links.all-users.href").description("사용자 전체 목록 조회하기")
            )
        ))
        .andDo(print())
    ;
    // @formatter:on

    // rest-doc + open api 3
    // @formatter:off
    mockMvc.perform(
        RestDocumentationRequestBuilders.get("/jpa/users/{id}", 1)
            .contentType(APPLICATION_JSON)
            .with(httpBasic("dev", "1234"))
        )
        .andExpect(status().isOk())
        .andDo(MockMvcRestDocumentationWrapper.document("retrieveUser",
            resource(
                ResourceSnippetParameters.builder()
                    .description("사용자 정보 조회하기")
                    .summary("사용자 정보")
                    .requestHeaders(
                        headerWithName("Authorization").description("Basic auth credentials")
                    )
                    .pathParameters(
                        parameterWithName("id").description("사용자 아이디")
                    )
                    .links(
                        linkWithRel("all-users").description("사용자 전체 목록 조회하기")
                    )
                    .responseFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
                        fieldWithPath("joinDate").type(JsonFieldType.STRING).description("사용자 등록일"),
                        fieldWithPath("ssn").type(JsonFieldType.STRING).description("사용자 주민번호"),
                        fieldWithPath("_links.all-users.href").description("사용자 전체 목록 조회하기")
                    )
                    .build()
            )
        ))
        .andDo(print())
    ;
    // @formatter:on
  }

}
