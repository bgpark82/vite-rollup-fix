import {Create, SimpleForm, TextInput} from "react-admin";

export const TemplateCreate = () => {
    return (
        <Create title="템플릿 생성">
            <SimpleForm sx={{width: "100%"}} >
                <TextInput source="template"/>
                <TextInput resettable
                           label=""
                           source="name"
                           placeholder="데이터플랫폼 템플릿"
                           helperText="템플릿 이름"/>
                <TextInput resettable
                           label=""
                           source="interval"
                           placeholder="* * * * *"
                           helperText="배치가 실행되는 주기"
                />
                <TextInput resettable
                           label=""
                           source="ttl"
                           placeholder="86400"
                           helperText="레디스의 ttl (미입력시 기본 3일)"
                />
                <TextInput resettable
                           label=""
                           source="userId"
                           placeholder="peter.park"
                           helperText="@musinsa.com을 제외한 이메일"
                />
                <TextInput source="params"/>
                <TextInput source="alias"/>
            </SimpleForm>
        </Create>
    )
};
