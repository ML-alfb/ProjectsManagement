import React from "react";
import { useForm } from "react-hook-form";
import { useDispatch } from "react-redux";
import { registerUser } from "../../../features/auth/registerSlice";

const RegisterForm = () => {
  const {
    register,
    handleSubmit,
    formState: { errors, isValid, isSubmitting },
    watch,
  } = useForm();
  const dispatch = useDispatch();

  const onSubmit = (data) => {
    console.log(data);
    dispatch(registerUser(data));
  };

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className="max-w-md mx-auto mt-8 bg-white p-8 rounded-xl shadow-lg"
    >
      <div className="flex w-full items-center justify-center p-2 text-2xl font-bold">
        Sign in
      </div>
      <div className="mb-4">
        <label className="block text-gray-700 text-sm font-bold mt-3 mb-2">
          UserName
        </label>
        <input
          className=" appearance-none outline-none bg-transparent w-full py-2 px-3 textgray-700 leading-tight border-b border-gray-400"
          type="text"
          placeholder="userName"
          {...register("userName", { required: true })}
        />
        {errors.userName && (
          <span className="text-red-500">UserName is required</span>
        )}
      </div>
      <div className="mb-4">
        <label className="block text-gray-700 text-sm font-bold mt-3 mb-2">
          FirstName
        </label>
        <input
          className=" appearance-none outline-none bg-transparent w-full py-2 px-3 textgray-700 leading-tight border-b border-gray-400"
          type="text"
          placeholder="firstName"
          {...register("firstName", { required: true })}
        />
        {errors.firstName && (
          <span className="text-red-500">FirstName is required</span>
        )}
      </div>
      <div className="mb-4">
        <label className="block text-gray-700 text-sm font-bold mt-3 mb-2">
          LastName
        </label>
        <input
          className=" appearance-none outline-none bg-transparent w-full py-2 px-3 textgray-700 leading-tight border-b border-gray-400"
          type="text"
          placeholder="lastName"
          {...register("lastName", { required: true })}
        />
        {errors.lastName && (
          <span className="text-red-500">LastName is required</span>
        )}
      </div>

      <div className="mb-4">
        <label className="block text-gray-700 text-sm font-bold mt-3 mb-2">
          Email
        </label>
        <input
          className=" appearance-none outline-none bg-transparent w-full py-2 px-3 textgray-700 leading-tight border-b border-gray-400"
          type="email"
          placeholder="example@gmail.com"
          {...register("email", { required: true })}
        />
        {errors.email && (
          <span className="text-red-500">Email is required</span>
        )}
      </div>

      <div className="mb-4">
        <label className="block text-gray-700 text-sm font-bold mt-3 mb-2">
          Password
        </label>
        <input
          className=" appearance-none outline-none bg-transparent w-full py-2 px-3 textgray-700 leading-tight border-b border-gray-400"
          type="password"
          placeholder="********"
          {...register("password", { required: true })}
        />
        {errors.password && (
          <span className="text-red-500">Password is required</span>
        )}
      </div>

      <div className="mb-4">
        <label className="block text-gray-700 text-sm font-bold mt-3 mb-2">
          Confirm Password
        </label>
        <input
          className="appearance-none outline-none bg-transparent w-full py-2 px-3 textgray-700 leading-tight border-b border-gray-400"
          type="password"
          placeholder="********"
          {...register("confirmationPassword", {
            required: "Confirm Password is required",
            validate: (value) =>
              value === watch("password") || "Passwords should match!",
          })}
        />
        {errors.confirmationPassword && (
          <span className="text-red-500">
            {errors.confirmationPassword.message}
          </span>
        )}
      </div>

      <button
        type="submit"
        className="text-white py-2 px-4 rounded w-full  focus:outline-none focus:shadow-outline-blue bg-btn"
        disabled={!isValid || isSubmitting}
      >
        Register
      </button>
    </form>
  );
};

export default RegisterForm;
