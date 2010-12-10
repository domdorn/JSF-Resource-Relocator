#! /bin/bash

SCHEMAPATH=src/main/resources/META-INF/jsf_resource_resolver_1_0.xsd
TESTBASEDIR=src/test/resources/config_files
CMD="xmllint --noout --schema"
$CMD ${SCHEMAPATH} ${TESTBASEDIR}/declare_resource.xml
$CMD ${SCHEMAPATH} ${TESTBASEDIR}/empty_config.xml
$CMD ${SCHEMAPATH} ${TESTBASEDIR}/exclude_resource.xml
$CMD ${SCHEMAPATH} ${TESTBASEDIR}/replace_resource.xml
$CMD ${SCHEMAPATH} ${TESTBASEDIR}/single_library.xml
$CMD ${SCHEMAPATH} ${TESTBASEDIR}/two_empty_mirrors.xml
$CMD ${SCHEMAPATH} ${TESTBASEDIR}/one_mirror_one_lib.xml
$CMD ${SCHEMAPATH} ${TESTBASEDIR}/update_admin_default_cred_disabled.xml
$CMD ${SCHEMAPATH} ${TESTBASEDIR}/update_disabled.xml
$CMD ${SCHEMAPATH} ${TESTBASEDIR}/update_enabled.xml
$CMD ${SCHEMAPATH} ${TESTBASEDIR}/jsf-resource-relocator_example_01.xml


#$CMD ${SCHEMAPATH} ${TESTBASEDIR}/invalid_config.xml