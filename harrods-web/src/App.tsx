import {Admin, Resource,} from "react-admin";
import {TemplateList} from "./templates/TemplateList";
import {TemplateShow} from "./templates/TemplateShow";
import {QueryList} from "./queries/QueryList";
import {QueryShow} from "./queries/QueryShow";
import {TemplateCreate} from "./templates/TemplateCreate";
import {dataProvider} from "./component/provider/DataProvider";

export const App = () =>
    <Admin dataProvider={dataProvider}>
        <Resource name="templates" list={TemplateList} show={TemplateShow} create={TemplateCreate}/>
        <Resource name="queries" list={QueryList} show={QueryShow}/>
    </Admin>
