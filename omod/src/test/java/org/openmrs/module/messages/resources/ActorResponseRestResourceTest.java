package org.openmrs.module.messages.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openmrs.api.context.Context;
import org.openmrs.module.messages.api.service.ActorResponseService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.powermock.core.classloader.annotations.PrepareForTest;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.List;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Context.class})
public class ActorResponseRestResourceTest  {

    private final ActorResponseRestResource actorResponseRestResource = new ActorResponseRestResource();

    @Mock
    private org.openmrs.module.messages.api.service.ActorResponseService ActorResponseService;
    
    @Mock
    private RequestContext requestContext;

    @Before
    public void setUp() {
        mockStatic(Context.class);
        when(Context.getService(ActorResponseService.class)).thenReturn(ActorResponseService);
    }
    
    @Test
    public void shouldGetAllAvailableRepresentations() {
        List<Representation> actual = actorResponseRestResource.getAvailableRepresentations();
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    public void shouldGetRefRepresentation() {
        DelegatingResourceDescription actual = actorResponseRestResource.getRepresentationDescription(new RefRepresentation());

        assertNotNull(actual);
        assertTrue(actual.getProperties().containsKey("id"));
        assertTrue(actual.getProperties().containsKey("patient"));
        assertTrue(actual.getProperties().containsKey("person"));
        assertTrue(actual.getProperties().containsKey("actor"));
        assertTrue(actual.getProperties().containsKey("textQuestion"));
        assertTrue(actual.getProperties().containsKey("textResponse"));
        assertTrue(actual.getProperties().containsKey("question"));
        assertTrue(actual.getProperties().containsKey("response"));
    }

    @Test
    public void shouldGetDefaultRepresentation() {
        DelegatingResourceDescription actual = actorResponseRestResource.getRepresentationDescription(new DefaultRepresentation());

        assertNotNull(actual);
        assertTrue(actual.getProperties().containsKey("id"));
        assertTrue(actual.getProperties().containsKey("patient"));
        assertTrue(actual.getProperties().containsKey("person"));
        assertTrue(actual.getProperties().containsKey("actor"));
        assertTrue(actual.getProperties().containsKey("textQuestion"));
        assertTrue(actual.getProperties().containsKey("textResponse"));
        assertTrue(actual.getProperties().containsKey("question"));
        assertTrue(actual.getProperties().containsKey("response"));
        assertTrue(actual.getProperties().containsKey("sourceId"));
        assertTrue(actual.getProperties().containsKey("sourceType"));
        assertTrue(actual.getProperties().containsKey("answeredTime"));
    }

    @Test
    public void shouldReturnNullWhenRepresentationIsDifferentThanRefAndDefault() {
        DelegatingResourceDescription actual = actorResponseRestResource.getRepresentationDescription(new FullRepresentation());

        assertNull(actual);
    }

    @Test
    public void shouldCreateCreatableProperties() {
        DelegatingResourceDescription actual = actorResponseRestResource.getCreatableProperties();
        assertNotNull(actual);
        assertTrue(actual.getProperties().containsKey("patient"));
        assertTrue(actual.getProperties().containsKey("person"));
        assertTrue(actual.getProperties().containsKey("actor"));
        assertTrue(actual.getProperties().containsKey("response"));
    }

    @Test
    public void shouldCreateUpdatableProperties() {
        DelegatingResourceDescription actual = actorResponseRestResource.getUpdatableProperties();
        assertNotNull(actual);
        assertTrue(actual.getProperties().containsKey("response"));
    }

    @Test
    public void shouldCheckIsRetirable() {
        assertTrue(actorResponseRestResource.isRetirable());
    }
 
}
