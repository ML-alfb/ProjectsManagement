import React from "react";
import { IoMdCheckmarkCircleOutline } from "react-icons/io";

const AcceptedDemandComponent = ({ demand }) => {
  return (
    <div className="my-1 px-2 py-6 w-full mx-auto bg-slate-50 rounded-xl shadow flex items-center space-x-2">
      <div className="shrink-0 ">
        <IoMdCheckmarkCircleOutline style={{ color: "green" }} size={40} />
      </div>
      <div>
        <div className="flex items-center gap-10">
          <div className="text-lg font-medium text-black">
            {demand?.projectName}
          </div>
          <p className="text-sm font-mono italic text-gray-500 float-right">
            {demand?.validationTime}
          </p>
        </div>
        <div className="text-base font-normal text-gray-500">
          {demand?.projectLongName}
        </div>
        <p className="text-gray-800">
          You have successfully created
          <em className="ml-2 font-bold text-xs text-black">Project-Manag</em>{" "}
        </p>
      </div>
    </div>
  );
};

export default AcceptedDemandComponent;
