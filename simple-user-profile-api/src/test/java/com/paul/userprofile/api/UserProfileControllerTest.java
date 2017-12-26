package com.paul.userprofile.api;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(UserProfileController.class)
public class UserProfileControllerTest {

    private MockMvc underTest;

    @MockBean
    private UserProfileRepository userProfileRepository;

    @Before
    public void setUp() throws Exception {
        underTest = MockMvcBuilders.standaloneSetup(new UserProfileController(userProfileRepository)).build();
    }

    @Test
    public void shouldGetUserProfiles() throws Exception {

        UserProfile userProfile = new UserProfile();
        userProfile.setId(1L);
        userProfile.setFirstName("Test");
        userProfile.setLastName("Test");
        userProfile.setBirthDate(new Date());

        List<UserProfile> actualList = Arrays.asList(userProfile);
        Mockito.when(userProfileRepository.findAll()).thenReturn(actualList);

        ResultActions perform = this.underTest.perform(get("/user-profiles").accept(MediaType.APPLICATION_JSON));
        perform.andExpect(status().isOk());


        MvcResult result = perform.andReturn();
        String resultContent = result.getResponse().getContentAsString();

        ObjectMapper om = new ObjectMapper();
        String resultExpected = om.writeValueAsString(actualList);

        assertTrue("Resulted user profile list should match with actual one", resultContent.equals(resultExpected));
    }
    
    
    @Test
    public void shouldGetUserProfileById() throws Exception {
        long id = 1L;
        
        UserProfile userProfile = new UserProfile();
        userProfile.setId(id);
        userProfile.setFirstName("Test");
        userProfile.setLastName("Test");
        userProfile.setBirthDate(new Date());

        Mockito.when(userProfileRepository.getOne(id)).thenReturn(userProfile);

        ResultActions perform = this.underTest.perform(get("/user-profiles/" + id).accept(MediaType.APPLICATION_JSON));
        perform.andExpect(status().isOk());


        MvcResult result = perform.andReturn();
        String resultContent = result.getResponse().getContentAsString();

        ObjectMapper om = new ObjectMapper();
        String resultExpected = om.writeValueAsString(userProfile);

        assertTrue("Resulted user profile should match with actual one", resultContent.equals(resultExpected));
    }

    @Test
    public void shouldReturnNotFoundOnWrongId() throws Exception {
        long id = 1L;        
        
        UserProfile userProfile = new UserProfile();
        userProfile.setId(id);
        userProfile.setFirstName("Test");
        userProfile.setLastName("Test");
        userProfile.setBirthDate(new Date());

        Mockito.when(userProfileRepository.getOne(id)).thenReturn(userProfile);

        long wrongId = 2L;
        ResultActions perform = this.underTest.perform(get("/user-profiles/" + wrongId).accept(MediaType.APPLICATION_JSON));
        perform.andExpect(status().isNotFound());
    }
    
    
    @Test
    public void shouldSaveUserProfile() throws Exception {
        long id = 1L;        
        
        UserProfile userProfileToSave = new UserProfile();
        userProfileToSave.setFirstName("Test");
        userProfileToSave.setLastName("Test");
        userProfileToSave.setBirthDate(new Date());
        
        
        UserProfile userProfileToReturn = new UserProfile();
        userProfileToReturn.setId(id);
        userProfileToReturn.setFirstName("Test");
        userProfileToReturn.setLastName("Test");
        userProfileToReturn.setBirthDate(new Date());


        Mockito.when(userProfileRepository.save(userProfileToSave)).thenReturn(userProfileToReturn);

        ObjectMapper om = new ObjectMapper();
        String contentToSave = om.writeValueAsString(userProfileToSave);
        
        ResultActions perform = this.underTest.perform(post("/user-profiles").contentType(MediaType.APPLICATION_JSON_VALUE).content(contentToSave));
        perform.andExpect(status().isOk());
        
        
        MvcResult result = perform.andReturn();
        String resultContent = result.getResponse().getContentAsString();

        String resultExpected = om.writeValueAsString(userProfileToReturn);
        assertTrue("Saved user profile should match with expected one", resultContent.equals(resultExpected));
    }
    
    
    @Test
    public void shouldDeleteUserProfile() throws Exception {
        long id = 1L;
        Mockito.doNothing().when(userProfileRepository).delete(id);
        
        ResultActions perform = this.underTest.perform(delete("/user-profiles/" + id));
        perform.andExpect(status().isOk());
    }


}
