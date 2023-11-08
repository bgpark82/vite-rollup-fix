import {Admin, Resource,} from "react-admin";
import {harrodsDataProvider} from "../provider/HarrodsProvider";
import {TemplateList} from "./templates/TemplateList";
import {TemplateShow} from "./templates/TemplateShow";

export const App = () => <Admin dataProvider={harrodsDataProvider}>
    <Resource name="templates" list={TemplateList} show={TemplateShow} />
    <Resource name="queries"/>
</Admin>;
