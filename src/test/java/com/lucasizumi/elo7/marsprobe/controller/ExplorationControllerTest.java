package com.lucasizumi.elo7.marsprobe.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.lucasizumi.elo7.marsprobe.domain.Mars;
import com.lucasizumi.elo7.marsprobe.domain.Position;
import com.lucasizumi.elo7.marsprobe.domain.Probe;
import com.lucasizumi.elo7.marsprobe.dto.ProbeDto;
import com.lucasizumi.elo7.marsprobe.engine.MovementEngine;
import com.lucasizumi.elo7.marsprobe.exception.LimitViolationException;
import com.lucasizumi.elo7.marsprobe.exception.UniqueKeyException;
import com.lucasizumi.elo7.marsprobe.service.ExplorationService;

@RunWith(MockitoJUnitRunner.class)
public class ExplorationControllerTest {

	private static final String IDENTIFICATION = "Probe Test";
	private static final int X_AXIS_INITIAL = 0;
	private static final int Y_AXIS_INITIAL = 0;
	private static final String COMMAND  = "MMLMRM";
	private static final String DIRECTION  = "L";
	
	
	@InjectMocks
    private ExplorationController controller;

	@Mock
	private ExplorationService service;
	
	@Mock
	private MovementEngine movementEngine;
	
	@Mock
	private Mars mars;

	@Rule
    public ExpectedException expectedException = ExpectedException.none();
	
	private MockMvc mockMvc;
	
	@Before
    public void setUp() {
    	mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
	
	
	@Test
	public void createMarsAlreadyExistsBecauseMarsIsMockedHereTest() throws Exception {
		
		String json = "{\"limitAxisX\": 6, " +
                		"\"limitAxisY\": 6}";
		
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/exploration/mars")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isConflict());
	}
	
	@Test
	public void sendProbeToMarsTest() throws Exception {
		
		Mockito.when(service.sendProbeToMars(Mockito.eq(mars), Mockito.any(ProbeDto.class))).thenReturn(getProbe());
		
		String json = "{\"identification\":\"Probe Test\", " +
                "\"initialAxisX\": 0, " +
                "\"initialAxisY\": 0}";

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/exploration/mars/probe")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

		
        resultActions.andExpect(status().isCreated())
        .andExpect(jsonPath("$.identification").value("Probe Test"))
        .andExpect(jsonPath("$.direction").value("N"))
        .andExpect(jsonPath("$.position").value("(0,0)"));
	}
	
	@Test
	public void sendAlreadyExistsProbeToMarsTest() throws Exception {
		
		Mockito.when(service.sendProbeToMars(Mockito.eq(mars), Mockito.any(ProbeDto.class))).thenThrow(UniqueKeyException.class);
		
		String json = "{\"identification\":\"Probe Test\", " +
                "\"initialAxisX\": 0, " +
                "\"initialAxisY\": 0}";

		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/exploration/mars/probe")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
		
		resultActions.andExpect(status().isConflict());
	}
	
	@Test
	public void sendProbeToMarsOutOfBoundsTest() throws Exception {
		Mockito.when(service.sendProbeToMars(Mockito.eq(mars), Mockito.any(ProbeDto.class))).thenThrow(LimitViolationException.class);
		
		String json = "{\"identification\":\"Probe Test\", " +
                "\"initialAxisX\": 0, " +
                "\"initialAxisY\": 0}";

		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/exploration/mars/probe")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
		
		resultActions.andExpect(status().isConflict());
	}
	
	@Test
	public void singleMoveProbeTest() throws Exception {
		Mockito.when(service.moveProbe(Mockito.eq(mars), Mockito.anyString())).thenReturn(getProbe());
		
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch("/exploration/probe/{probeIdentifier}/move", IDENTIFICATION));
		
		resultActions.andExpect(status().isOk());
		
	}
	
	@Test
	public void moveWithCommandProbeTest() throws Exception {
		Mockito.when(service.moveProbe(Mockito.eq(mars), Mockito.anyString(), Mockito.anyString())).thenReturn(getProbe());
		
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch("/exploration/probe/{probeIdentifier}/move/{command}", IDENTIFICATION, COMMAND));
		
		resultActions.andExpect(status().isOk());
	}
	
	@Test
	public void rotateProbeTeste() throws Exception {
		Mockito.when(service.rotateProbe(Mockito.eq(mars), Mockito.anyString(), Mockito.anyChar())).thenReturn(getProbe());
		
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch("/exploration/probe/{probeIdentifier}/rotate/{rotateDirection}", IDENTIFICATION, DIRECTION));
		
		resultActions.andExpect(status().isOk());
	}
	
	
	private Probe getProbe() {
		return new Probe(IDENTIFICATION, new Position(X_AXIS_INITIAL, Y_AXIS_INITIAL));
	}
	
}
