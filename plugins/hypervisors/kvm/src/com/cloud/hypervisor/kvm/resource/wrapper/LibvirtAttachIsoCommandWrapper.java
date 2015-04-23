//
// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
//

package com.cloud.hypervisor.kvm.resource.wrapper;

import java.net.URISyntaxException;

import org.libvirt.Connect;
import org.libvirt.LibvirtException;

import com.cloud.agent.api.Answer;
import com.cloud.agent.api.AttachIsoCommand;
import com.cloud.exception.InternalErrorException;
import com.cloud.hypervisor.kvm.resource.LibvirtComputingResource;
import com.cloud.resource.CommandWrapper;

public final class LibvirtAttachIsoCommandWrapper extends CommandWrapper<AttachIsoCommand, Answer, LibvirtComputingResource> {

    @Override
    public Answer execute(final AttachIsoCommand command, final LibvirtComputingResource libvirtComputingResource) {
        try {
            final LibvirtConnectionWrapper libvirtConnectionWrapper = libvirtComputingResource.getLibvirtConnectionWrapper();

            final Connect conn = libvirtConnectionWrapper.getConnectionByVmName(command.getVmName());
            libvirtComputingResource.attachOrDetachISO(conn, command.getVmName(), command.getIsoPath(), command.isAttach());
        } catch (final LibvirtException e) {
            return new Answer(command, false, e.toString());
        } catch (final URISyntaxException e) {
            return new Answer(command, false, e.toString());
        } catch (final InternalErrorException e) {
            return new Answer(command, false, e.toString());
        }

        return new Answer(command);
    }
}