import { useState } from "react";
import {
  RouterProvider,
  createBrowserRouter,
  createRoutesFromElements,
  Route,
} from "react-router-dom";
import "./App.css";
import LoginPage from "./pages/auth/LoginPage";
import ErrorPage from "./pages/error/ErrorPage";
import PrivateRoutes from "./utils/PrivateRoutes";
import RegisterPage from "./pages/auth/RegisterPage";
const router = createBrowserRouter(
  createRoutesFromElements(
    <Route>
      <Route element={<PrivateRoutes navto="/login" isLogedIn={true} />}>
        <Route path="/" element={<div>home</div>} />
        <Route path="/home" element={<div>home</div>} />
      </Route>

      <Route element={<PrivateRoutes navto="/home" isLogedIn={false} />}>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
      </Route>

      <Route path="*" element={<ErrorPage />} />
    </Route>
  )
);
function App() {
  return <RouterProvider router={router} />;
}

export default App;
