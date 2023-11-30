import {defineConfig} from 'vite';
import react from '@vitejs/plugin-react';
import * as nodepath from "path"
import * as fs from "fs";
import * as dotenv from "dotenv"


// https://vitejs.dev/config/
export default (() => {

    // .env.local의 환경변수를 추가한다
    if (process.env.NODE_ENV === 'local') {
        const path = nodepath.join(process.cwd(), '.env.local');
        if (fs.existsSync(path)) {
            const localEnv = dotenv.parse(fs.readFileSync(path));
            process.env = {...process.env, ...localEnv}
        }
    }

    return defineConfig({
        plugins: [react()],
        define: {
            'process.env': process.env,
        },
        server: {
            host: true,
        },
        base: './',
        build: {
            rollupOptions: {
                external: ['@uiw/react-codemirror', 'sql-formatter']
            }
        },
    })
});
