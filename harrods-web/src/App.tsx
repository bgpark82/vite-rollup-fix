import {Admin,} from "react-admin";
import {harrodsDataProvider} from "../provider/HarrodsProvider";

export const App = () => <Admin dataProvider={harrodsDataProvider}></Admin>;
