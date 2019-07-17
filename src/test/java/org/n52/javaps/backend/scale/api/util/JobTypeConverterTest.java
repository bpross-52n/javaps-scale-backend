package org.n52.javaps.backend.scale.api.util;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.n52.javaps.backend.scale.ScaleAlgorithm;
import org.n52.javaps.backend.scale.ScaleServiceController;
import org.n52.javaps.backend.scale.api.JobType;
import org.n52.javaps.description.TypedProcessDescription;

public class JobTypeConverterTest {

    private JobType jt;

    @Mock
    ScaleServiceController scaleService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void initJobType() {
        int id = 23;
        String name = "test-job-type-name";
        String version = "1.0";
        String title = "test-title";
        String description = "test-description";
        String category = "test-category";
        String author_name = "test-author-name";
        String author_url = "test-author-url";
        boolean is_system = false;
        boolean is_long_running = false;
        boolean is_active = true;
        boolean is_operational = true;
        boolean is_paused = false;
        String icon_code = "f013";
        boolean uses_docker = false;
        boolean docker_privileged = false;
        String docker_image = null;
        int revision_num = 1;
        int priority = 1;
        int timeout = 0;
        int max_scheduled = 1;
        int max_tries = 0;
        double cpus_required = 0.5;
        double mem_required = 64.0;
        double mem_const_required = 64.0;
        double mem_mult_required = 0.0;
        double shared_mem_required = 0.0;
        double disk_out_const_required = 64.0;
        double disk_out_mult_required = 0.0;
        String created = "2019-07-17T23:52:42Z";
        String archived = null;
        String paused = null;
        String last_modified = "2019-07-17T23:52:42Z";

        jt = new JobType();

        jt.setId(id);
        jt.setName(name);
        jt.setVersion(version);
        jt.setTitle(title);
        jt.setDescription(description);
        jt.setCategory(category);
        jt.setAuthor_name(author_name);
        jt.setAuthor_url(author_url);
        jt.setIs_system(is_system);
        jt.setIs_long_running(is_long_running);
        jt.setIs_active(is_active);
        jt.setIs_operational(is_operational);
        jt.setIs_paused(is_paused);
        jt.setIcon_code(icon_code);
        jt.setUses_docker(uses_docker);
        jt.setDocker_privileged(docker_privileged);
        jt.setDocker_image(docker_image);
        jt.setRevision_num(revision_num);
        jt.setPriority(priority);
        jt.setTimeout(timeout);
        jt.setMax_scheduled(max_scheduled);
        jt.setMax_tries(max_tries);
        jt.setCpus_required(cpus_required);
        jt.setMem_required(mem_required);
        jt.setMem_const_required(mem_const_required);
        jt.setMem_mult_required(mem_mult_required);
        jt.setShared_mem_required(shared_mem_required);
        jt.setDisk_out_const_required(disk_out_const_required);
        jt.setDisk_out_mult_required(disk_out_mult_required);
        jt.setCreated(created);
        jt.setArchived(archived);
        jt.setPaused(paused);
        jt.setLast_modified(last_modified);
    }

    @Test
    public void testConvertToAlgorithm() {
        ScaleAlgorithm scaleAlgorithm = new JobTypeConverter(scaleService).convertToAlgorithm(jt);
        assertThat(scaleAlgorithm, is(notNullValue()));
        assertThat(scaleAlgorithm.getDescription(), is(notNullValue()));
        TypedProcessDescription description = scaleAlgorithm.getDescription();
        assertThat(description.getId().getValue(), is("scale-algorithm-test-job-type-name-v1.0-r1"));
    }

}
