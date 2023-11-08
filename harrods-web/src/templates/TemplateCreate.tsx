import {Create, SimpleForm, TextInput, required, Labeled} from "react-admin";
import QueryInput from "../component/input/QueryInput";
import JsonInput from "../component/input/JsonInput";
import {Grid} from "@mui/material";

export const TemplateCreate = () => {
    return (
        <Create title="템플릿 생성">
            <SimpleForm sx={{width: "80%"}} >
                <Grid container rowSpacing={3}>
                    <Grid item xs={12}>
                        <Labeled label="템플릿" isRequired>
                            <QueryInput source="template"
                                        height="400px"
                                        width="1000px"
                                        validate={required("템플릿은 필수값입니다")}
                            />
                        </Labeled>
                    </Grid>
                    <Grid item xs={3}>
                        <Labeled label="템플릿 이름">
                            <TextInput resettable
                                       label=""
                                       source="name"
                                       placeholder="데이터플랫폼 템플릿"
                                       helperText="템플릿 이름"
                            />
                        </Labeled>
                    </Grid>
                    <Grid item xs={3}>
                        <Labeled label="interval" isRequired >
                            <TextInput resettable
                                       label=""
                                       source="interval"
                                       placeholder="* * * * *"
                                       helperText="배치가 실행되는 주기"
                                       validate={required("interval은 필수값입니다")}
                            />
                        </Labeled>
                    </Grid>
                    <Grid item xs={3}>
                        <Labeled label="ttl">
                            <TextInput resettable
                                       label=""
                                       source="ttl"
                                       placeholder="86400"
                                       helperText="레디스의 ttl (미입력시 기본 3일)"
                            />
                        </Labeled>
                    </Grid>
                    <Grid item xs={3}>
                        <Labeled label="등록인 아이디" width="300px" isRequired>
                            <TextInput resettable
                                       label=""
                                       source="userId"
                                       placeholder="peter.park"
                                       helperText="@musinsa.com을 제외한 이메일"
                                       validate={required("등록인 아이디는 필수값입니다")}
                            />
                        </Labeled>
                    </Grid>
                    <Grid item xs={6}>
                        <Labeled label="파라미터" >
                            <JsonInput source="params"
                                       height="100px"
                                       width="400px"
                                       placeholder='{ "username": "peter.park" }'
                            />
                        </Labeled>
                    </Grid>
                    <Grid item xs={6}>
                        <Labeled label="별칭" isRequired >
                            <JsonInput source="alias"
                                       height="100px"
                                       width="400px"
                                       placeholder='[ "alias", "username" ]'
                                       validate={[required("별칭은 필수값입니다")]}
                            />
                        </Labeled>
                    </Grid>
                </Grid>
            </SimpleForm>
        </Create>
    )
};
